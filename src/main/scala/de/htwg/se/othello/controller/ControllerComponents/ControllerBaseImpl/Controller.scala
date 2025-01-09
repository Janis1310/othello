package de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl

import de.htwg.se.othello.ai.StrategyContext
import de.htwg.se.othello.controller.ControllerComponents.{ControllerComponent, GameState}
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone, Stoneposition}
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.CommandComponents.CommandBaseImpl.{SetCommand, UndoManager}
import de.htwg.se.othello.model.CommandComponents.UndoManagerComponent
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandler
import de.htwg.se.othello.model.HandlerComponents.MoveHandlerTemplateInterface
import de.htwg.se.othello.model.Playercomponents.Player

import scala.collection.immutable.Queue
import scala.io.StdIn.readLine
import scala.util.{Failure, Success, Try}


class Controller(var board: BoardComponent) extends ControllerComponent{
  private var players: Queue[Player] = Queue()
  private var gameState: GameState.GameState = GameState.SETUP
  private val moveHandler: MoveHandlerTemplateInterface = MoveHandler
  private val undoManager = new UndoManager

  // Gibt das Board als Zeichenkette zurück
  def boardToString: String = board.toString

  // Spieler hinzufügen und den aktuellen Zustand zurückgeben
  def addPlayers(player1Name: String,player2Name: String): Unit = {
     if (gameState == GameState.SETUP) {
      players = Queue(
        Player(player1Name, Stone.White,"Human" ),
        Player(player2Name, Stone.Black, "Human" )
      )
    } else {
      println("Spieler können nur im SETUP-Zustand hinzugefügt werden.")
    }
  }
  def getPlayers: Queue[Player] = players

  def getCurrentPlayer: Player = players.head

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

  // Führt einen Zug aus und gibt den neuen Zustand des Spiels zurück
  def makeMove(x: Int, y: Int): Boolean = {
     gameState match {
      case GameState.WHITE_TURN | GameState.BLACK_TURN =>
        val stone = getCurrentPlayer.stone
        val stonePosition = Stoneposition(x, y, stone)

        val previousBoard = board.copy()

        val moveresult = Try {
          
           // Nach einem gültigen Zug den Spieler wechseln
          undoManager.doStep(new SetCommand(previousBoard, moveHandler.processMove(stonePosition, board), this))
          board.toString
        }

        moveresult match {
        case Success(boardString) =>
          nextPlayer()
          true // Erfolgreiches Ergebnis, gibt das Board als String zurück
        case Failure(_) => 
          println("Ungültiger Zug...") // Fehler im Zug
          false
      }

      case _ =>
        println("Züge sind nur während eines Spielzugs erlaubt.");
        false
    }
  }

  // Erstellt ein neues Board und gibt es zurück
  def createNewBoard(rows: Int, cols: Int): BoardComponent = {
    if (gameState == GameState.SETUP) {
      board = new Board(rows, cols)
      changeState(GameState.WHITE_TURN) // Erst nach Erstellung des Boards den Zustand ändern
      notifyObservers
      board
    } else {
      println("Das Board kann nur im SETUP-Zustand erstellt werden.")
      board
    }
  }

   def changeState(newState: GameState.GameState): Unit = {
    gameState = newState
    GameState.action(gameState)
    //notifyObservers
  }

  def getGameState: GameState.GameState = gameState


  // Verarbeitet den nächsten Zug, entweder vom menschlichen Spieler oder von der KI
  def processTurn(curRow : Int, curCol : Int): Boolean = { // wird von Tui aufgerufen
    val currentPlayer = getCurrentPlayer
    if (currentPlayer.role == "AI") {
      println("KI ist am ZugTesteawdownjoiawdjipdawdawkoiawdkopwdokawdokpawdokado")
      StrategyContext.setPlayers(players)
      val strategy = StrategyContext.strategy // Hole die Strategie der KI
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

  // ab hier für Command Pattern
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
  notifyObservers // Observer über den neuen Zustand informieren
  }

  /*def getBoard() : BoardComponent = {
    board
  }*/
}