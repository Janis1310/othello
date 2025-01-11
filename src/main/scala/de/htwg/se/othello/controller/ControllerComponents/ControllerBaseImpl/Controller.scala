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


class Controller @Inject()(var board: BoardComponent, val undoManager : UndoManagerComponent, val moveHandler : MoveHandlerTemplateInterface) extends ControllerComponent {
  private var players: Queue[Player] = Queue()
  private var gameState: GameState.GameState = GameState.SETUP

  def boardToString: String = board.toString

  def addPlayers(playerName: String): Unit = {
    if (gameState == GameState.InputPlayer1) {
    // Spieler 1 hinzufügen (Weiß)
    players = players :+ Player(playerName, Stone.WhiteStone, "Human")  // Spieler zu Queue hinzufügen
    changeState(GameState.InputPlayer2)  // Zustand ändern zu InputPlayer2
  } else if (gameState == GameState.InputPlayer2) {
    // Spieler 2 hinzufügen (Schwarz)
    players = players :+ Player(playerName, Stone.BlackStone, "Human")  // Spieler zu Queue hinzufügen
    println(s"${players.head.name} und ${players.last.name} wurden hinzugefügt!")  // Spieler 1 und Spieler 2 anzeigen
  } else {
    println("Spieler können nur im SETUP-Zustand hinzugefügt werden.")
  }
      
    
  }

  def getPlayers: Queue[Player] = players

  def getCurrentPlayer: Player = players.head

  def getGameState: GameState.GameState = gameState

  def nextPlayer(): Unit = {
    val (currentPlayer, updatedQueue) = players.dequeue
    players = updatedQueue.enqueue(currentPlayer)
    gameState match {
      case GameState.WHITE_TURN => changeState(GameState.BLACK_TURN)
      case GameState.BLACK_TURN => changeState(GameState.WHITE_TURN)
      case _ => println("Spielerwechsel ist im aktuellen Zustand nicht möglich.")
    }
    notifyObservers
  }

  def getBoard: BoardComponent = board

  def createNewBoard(rows: Int, cols: Int): BoardComponent = {
    if (gameState == GameState.InputBoardSize) {
      board = new Board(rows, cols)
      
      notifyObservers
      // changeState(GameState.WHITE_TURN)

      board
    } else {
      println("Das Board kann nur im SETUP-Zustand erstellt werden.")
      board
    }
  }

  def changeState(newState: GameState.GameState): Unit = {
    gameState = newState
    if (gameState == GameState.BLACK_TURN || gameState == GameState.WHITE_TURN)
      print(s"${getCurrentPlayer.name}, Du bist dran. Dein Stein ist: ")
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
            println("Ungültiger Zug...")
            false
        }

      case _ =>
        println("Züge sind nur während eines Spielzugs erlaubt.");
        false
    }
  }

  def processTurn(curRow: Int, curCol: Int): Boolean = {
    val currentPlayer = getCurrentPlayer
    if (currentPlayer.role == "AI") {
      println("KI ist am Zug")
      StrategyContext.setPlayers(players)
      val strategy = StrategyContext.strategy
      strategy(board) match {
        case Some(move) =>
          println(s"Die KI macht den Zug ${move}.")
          if (makeMove(move.x, move.y)) {
            println("Zug erfolgreich!")
            true
          } else {
            println(s"Fehler")
            sys.exit(1)
          }

        case None =>
          println("Die KI konnte keinen gültigen Zug finden. Das Spiel ist vorbei!")
          System.exit(0)
          false
      }
    } else {
      if (makeMove(curRow, curCol)) {
        true
      } else {
        false
      }
    }
  }

  def undo: Unit = {
    undoManager.undoStep()
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep()
    notifyObservers
  }

  def setBoard(board: BoardComponent): Unit = {
    this.board = board
    //notifyObservers
  }
}