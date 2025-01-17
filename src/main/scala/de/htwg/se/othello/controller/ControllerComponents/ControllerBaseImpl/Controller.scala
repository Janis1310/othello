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
import de.htwg.se.othello.OthelloModule
import com.google.inject.Guice
import de.htwg.se.othello.model.FileIOComponent.FileIOInterface
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandler


class Controller @Inject()(var board: BoardComponent, val undoManager: UndoManagerComponent, val moveHandler: MoveHandlerTemplateInterface) extends ControllerComponent {
  private var players: Queue[Player] = Queue()
  private var gameState: GameState.GameState = GameState.SETUP
  private var gameMode: String = ""
  val injector = Guice.createInjector(new OthelloModule)
  val fileIo = injector.getInstance(classOf[FileIOInterface])

  def boardToString: String = board.toString

  def addPlayers(playerName: String): Unit = {
    if (gameState == GameState.InputPlayer1) {
      players = players :+ Player(playerName, Stone.WhiteStone, "Human")
      if (gameMode == "Human") {
        changeState(GameState.InputPlayer2)
      } else {
        players = players :+ Player("AI", Stone.BlackStone, "AI")
        println(s"${players.head.name} wurde hinzugefügt!")
        Player.setPlayers(players)
      }
    } else if (gameState == GameState.InputPlayer2) {
      players = players :+ Player(playerName, Stone.BlackStone, "Human")
      println(s"${players.head.name} und ${players.last.name} wurden hinzugefügt!")
      Player.setPlayers(players)
    } else {
      throw new IllegalStateException("Spieler können nur im InputPlayer Zustand hinzugefügt werden")
    }
  }


  def setGameMode(mode: String): Unit = {
    gameMode = mode
  }

  def getGameMode: String = gameMode

  def getPlayers: Queue[Player] = Player.getPlayers

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
    if (!MoveHandler.isAnyMovePossible(board, getCurrentPlayer.stone)) {
      println("Es gibt keinen gültigen Zug. Kein Spielerwechsel!")
      val (currentPlayer, updatedQueue) = players.dequeue
      players = updatedQueue.enqueue(currentPlayer)
      gameState match {

        case GameState.WHITE_TURN => changeState(GameState.BLACK_TURN)
        case GameState.BLACK_TURN => changeState(GameState.WHITE_TURN)
      }
      if(!MoveHandler.isAnyMovePossible(board, getCurrentPlayer.stone)) {
        println("Das Spiel ist zu ende. Es gibt keine legalen Züge mehr.")
        //end()
      }
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
    Player.setPlayers(players)
    val strategy = StrategyContext.strategy

    strategy(board) match {
      case Some(move) =>
        println(s"Die KI macht den Zug (${move.x}, ${move.y}).")
        makeMove(move.x, move.y)
 

      case None =>
        false
    }
  }

  def undo: Unit = {
    val board = this.getBoard
    undoManager.undoStep()
    if (!this.boardToString.replaceAll("\\s+", "").equals(board.toString.replaceAll("\\s+", ""))) {
      nextPlayer()
      notifyObservers
    }
  }

  def redo: Unit = {
    val board = this.getBoard
    undoManager.redoStep()
    if (!this.boardToString.replaceAll("\\s+", "").equals(board.toString.replaceAll("\\s+", ""))) {
      nextPlayer()
      notifyObservers
    }
  }

  def setBoard(board: BoardComponent): Unit = {
    this.board = board
    //notifyObservers
  }

  def save(): Unit = {
    fileIo.save(board, players.head, players.last, gameMode)
  }

  def load(): Unit = {
    val (loadedBoard, loadedCurrentPlayer, loadedNextPlayer, loadedMode) = fileIo.load()
    board = loadedBoard
    players = players.enqueue(loadedCurrentPlayer).enqueue(loadedNextPlayer)
    gameMode = loadedMode
    if (getCurrentPlayer.stone == Stone.Black) {
      changeState(GameState.BLACK_TURN)
    } else {
      changeState(GameState.WHITE_TURN)
    }
    notifyObservers
  }

  def countStone(): (Int, Int) = {
    val stones = for {
      row <- 0 until board.numRows
      col <- 0 until board.numCols
    } yield board.getBoard.cell(row, col)

    val blackCount = stones.count(_ == Stone.Black)
    val whiteCount = stones.count(_ == Stone.White)
    (whiteCount, blackCount)
  }
}