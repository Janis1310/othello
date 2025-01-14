package de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl

import de.htwg.se.othello.ai.StrategyContext
import de.htwg.se.othello.controller.ControllerComponents.{ControllerComponent, GameState}
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone, Stoneposition}
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.CommandComponents.CommandBaseImpl.SetCommand
import de.htwg.se.othello.model.CommandComponents.UndoManagerComponent
import de.htwg.se.othello.model.HandlerComponents.MoveHandlerTemplateInterface
import de.htwg.se.othello.model.Playercomponents.Player

import scala.collection.immutable.Queue
import scala.util.{Failure, Success, Try}
import com.google.inject.Inject
import de.htwg.se.othello.model.BoardComponents.MatrixInterface
import de.htwg.se.othello.model.BoardComponents.StoneComponent


class Controller @Inject()(var board: BoardComponent, val undoManager: UndoManagerComponent, val moveHandler: MoveHandlerTemplateInterface) extends ControllerComponent {
  private var players: Queue[Player] = Queue()
  private var gameState: GameState.GameState = GameState.SETUP
  private var gameMode: String = ""

  def boardToString: String = board.toString

  def addPlayers(playerName: String): Unit = {
    if (gameState == GameState.InputPlayer1) {
      players = players :+ Player(playerName, Stone.WhiteStone, "Human")
      if (gameMode == "Human") {
        changeState(GameState.InputPlayer2)
      } else {
        players = players :+ Player("AI", Stone.BlackStone, "AI")
        println(s"${players.head.name} wurde hinzugefügt!")
      }
    } else if (gameState == GameState.InputPlayer2) {
      players = players :+ Player(playerName, Stone.BlackStone, "Human")
      println(s"${players.head.name} und ${players.last.name} wurden hinzugefügt!")
    } else {
      throw new IllegalStateException("Spieler können nur im InputPlayer Zustand hinzugefügt werden")    }
  }

  def setGameMode(mode: String): Unit = {
    gameMode = mode
  }

  def getGameMode: String = gameMode

  def getPlayers: Queue[Player] = players

  def getCurrentPlayer: Player = players.head

  def getGameState: GameState.GameState = gameState

  def nextPlayer(): Unit = {
    val (currentPlayer, updatedQueue) = players.dequeue
    players = updatedQueue.enqueue(currentPlayer)
    gameState match {
      case GameState.WHITE_TURN => changeState(GameState.BLACK_TURN)
      case GameState.BLACK_TURN => changeState(GameState.WHITE_TURN)
      case _ => throw new IllegalStateException("Spielerwechsel ist im aktuellen Zustand nicht möglich.")
    }
    notifyObservers
  }

  def getBoard: BoardComponent = board

  def createNewBoard(rows: Int, cols: Int): BoardComponent = {
    if (gameState == GameState.InputBoardSize) {
      board = new Board(rows, cols)
      changeState(GameState.WHITE_TURN)

      board
    } else {
      throw new IllegalStateException("Das Board kann nur im InputBoardSize-Zustand erstellt werden.")
    }
  }

  def changeState(newState: GameState.GameState): Unit = {
    gameState = newState
    if (gameState != GameState.WHITE_TURN)
      GameState.action(gameState)
    //notifyObservers
  }

  def makeMove(x: Int, y: Int): Boolean = {
    gameState match {
      case GameState.WHITE_TURN | GameState.BLACK_TURN =>
        val stone = getCurrentPlayer.stone
        val stonePosition = Stoneposition(x, y, stone)

        val previousBoard = board.copy()

        val moveresult = Try {
          undoManager.doStep(new SetCommand(previousBoard, moveHandler.processMove(stonePosition, board), this))
        }
        moveresult match {
          case Success(boardString) =>
            nextPlayer()
            true
          case Failure(_) =>
            false
        }

      case _ =>
        throw new IllegalStateException("Züge sind nur während eines Spielzugs erlaubt.");
    }
  }
  def processAITurn(): Boolean = {
    StrategyContext.setPlayers(players)
    val strategy = StrategyContext.strategy

    strategy(board) match {
      case Some(move) =>
        println(s"Die KI macht den Zug (${move.x}, ${move.y}).")
        if (makeMove(move.x, move.y)) {
          println("Zug erfolgreich!")
          true
        } else {
          throw new IllegalArgumentException("Fehler: Der KI-Zug war ungültig.") // Das sollte niemals eintreten
        }

      case None =>
        false
    }
  }

  def undo: Unit = {
    undoManager.undoStep()
    nextPlayer()
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep()
    nextPlayer()
    notifyObservers
  }

  def setBoard(board: BoardComponent): Unit = {
    this.board = board
    //notifyObservers
  }

  def countStone():(Int, Int) = {
    val stones = for {
    row <- 0 until board.numRows
    col <- 0 until board.numCols
  } yield board.getBoard.cell(row, col)

    val blackCount = stones.count(_ == Stone.Black)
    val whiteCount = stones.count(_ == Stone.White)
    (whiteCount, blackCount)
  }
}