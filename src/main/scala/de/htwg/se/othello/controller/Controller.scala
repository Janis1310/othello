package de.htwg.se.othello.controller

import de.htwg.se.othello.model.{Board, Stone, Stoneposition, MoveHandler, Player}
import de.htwg.se.othello.ai.StrategyContext
import de.htwg.se.othello.util.{Observable, UndoManager}
import scala.collection.immutable.Queue
import scala.io.StdIn.readLine
import de.htwg.se.othello.model.handler.{MoveHandlerTemplate}



class Controller(var board: Board) extends Observable {
  private var players: Queue[Player] = Queue()
  private var gameState: GameState.GameState = GameState.SETUP
  private var moveHandler: MoveHandlerTemplate = MoveHandler

  // Gibt das Board als Zeichenkette zurück
  def boardToString: String = board.toString

  // Spieler hinzufügen und den aktuellen Zustand zurückgeben
  def addPlayers(player1Name: String,player2Name: String): Unit = {
     if (gameState == GameState.SETUP) {
      players = Queue(
        Player(player1Name, Stone.White,"Human" ),
        Player(player2Name, Stone.Black, "AI" )
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
  def makeMove(x: Int, y: Int): Either[String, String] = {
     gameState match {
      case GameState.WHITE_TURN | GameState.BLACK_TURN =>
        val stone = getCurrentPlayer.stone
        val stonePosition = Stoneposition(x, y, stone)

        try {
          board = moveHandler.processMove(stonePosition, board) // Template-Methode aufrufen
          nextPlayer() // Nach einem gültigen Zug den Spieler wechseln
          notifyObservers
          Right(boardToString)
        } catch {
           case _: IllegalArgumentException => Left(" Ungültiger Zug.")
        }

      case _ =>
        Left("Züge sind nur während eines Spielzugs erlaubt.")
    }
  }

  // Erstellt ein neues Board und gibt es zurück
  def createNewBoard(rows: Int, cols: Int): Board = {
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
    notifyObservers
  }

  def getGameState: GameState.GameState = gameState


  // Verarbeitet den nächsten Zug, entweder vom menschlichen Spieler oder von der KI
  def processTurn(): Unit = {
    val currentPlayer = getCurrentPlayer
    println("currentplayer.role: " + currentPlayer.role) // Tests
    if (currentPlayer.role == "AI") {
      println("KI ist am ZugTesteawdownjoiawdjipdawdawkoiawdkopwdokawdokpawdokado")
      StrategyContext.setPlayers(players)
      val strategy = StrategyContext.strategy // Hole die Strategie der KI
      strategy(board) match {
        case Some(move) =>
          println(s"Die KI macht den Zug ${move}.")
          makeMove(move.x, move.y) match  {
            case Right(updatedBoard) =>
              println("Zug erfolgreich! Aktuelles Spielfeld:")
              //println(updatedBoard)
              println("Test, nächster Spieler Test--------------------")
              //nextPlayer() // Spieler wechseln
            case Left(errorMessage) =>
              println(s"Fehler: $errorMessage")
              sys.exit(1)
          }
      case None =>
        println("Die KI konnte keinen gültigen Zug finden. Das Spiel ist vorbei!")
        System.exit(0)
      }
    } else {
      // Der aktuelle Spieler ist ein menschlicher Spieler, er muss seinen Zug manuell eingeben
      println(s"${currentPlayer.name}, du bist am Zug. Deine Farbe ist ${currentPlayer.stone}.")
      try {
        println("Gib die Koordinaten für deinen Zug im Format Zeile,Spalte ein:")
        val Array(x, y) = readLine().split(",").map(_.trim.toInt)
        makeMove(x, y) match {
          case Right(updatedBoard) =>
            println("Zug erfolgreich! Aktuelles Spielfeld:")
            // println(updatedBoard)
            //nextPlayer() // Spieler wechseln
          case Left(errorMessage) =>
            println(s"Fehler: $errorMessage")
        }
      } catch {
        case _: Exception =>
          println("Ungültige Eingabe. Bitte im Format Zeile,Spalte eingeben.")
      }
    }
  }

  // ab hier für Command Pattern
  var gameStatus: GameStatus = IDLE
  private val undoManager = new UndoManager
  def set(row: Int, col: Int, value: Int): Unit = {
    undoManager.doStep(new SetCommand(row, col, value, this))
    notifyObservers
  }

  def solve: Unit = {
    undoManager.doStep(new SolveCommand(this))
    notifyObservers
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }
}