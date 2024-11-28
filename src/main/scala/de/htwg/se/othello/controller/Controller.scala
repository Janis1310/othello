package de.htwg.se.othello.controller

import de.htwg.se.othello.model.{Board, Stone, Stoneposition, MoveHandler, Player}
import de.htwg.se.othello.ai.StrategyContext
import de.htwg.se.othello.util.Observable
import scala.collection.immutable.Queue
import scala.io.StdIn.readLine

class Controller(private var board: Board) extends Observable {
  private var players: Queue[Player] = Queue()

  // Gibt das Board als Zeichenkette zurück
  def boardToString: String = board.toString

  // Spieler hinzufügen und den aktuellen Zustand zurückgeben
  def addPlayers(player1Name: String, player2Name: String): Unit = {
    players = Queue(
      Player(player1Name, Stone.White, "Human"), // Der erste Spieler ist immer ein menschlicher Spieler
      Player(player2Name, Stone.Black, "AI") // Der zweite Spieler ist standardmäßig die KI
    )
    /*print("testtest")
    StrategyContext.setPlayers(players)
    print("testtest")*/
  }
  
  def getPlayers: Queue[Player] = players

  def getCurrentPlayer: Player = players.head

  def nextPlayer(): Unit = {
    val (currentPlayer, updatedQueue) = players.dequeue
    players = updatedQueue.enqueue(currentPlayer) // Aktuellen Spieler an das Ende stellen
    notifyObservers
  }

  // Führt einen Zug aus und gibt den neuen Zustand des Spiels zurück
  def makeMove(x: Int, y: Int): Either[String, String] = {
    val stone = getCurrentPlayer.stone
    val stonePosition = Stoneposition(x, y, stone)
    

    if (MoveHandler.isValidMove(stonePosition, board)) {
      board = MoveHandler.flipStones(stonePosition, board)
      notifyObservers
      Right(boardToString) // Erfolgreich, aktuelles Board zurückgeben
    } else {
      Left("Ungültiger Zug.") // Fehlernachricht zurückgeben
    }
  }

  // Erstellt ein neues Board und gibt es zurück
  def createNewBoard(rows: Int, cols: Int): Board = {
    board = new Board(rows, cols)
    notifyObservers
    board
  }

  // Verarbeitet den nächsten Zug, entweder vom menschlichen Spieler oder von der KI
  def processTurn(): Unit = {
    val currentPlayer = getCurrentPlayer
    if (currentPlayer.role == "AI") {
      StrategyContext.setPlayers(players) // für KI
      // Der aktuelle Spieler ist die KI, wir lassen die KI ihren Zug machen
      val strategy = StrategyContext.strategy // Hole die Strategie der KI
      strategy(board) match {
        case Some(move) =>
          println(s"Die KI hat den Zug ${move} gemacht.")
          makeMove(move.x, move.y) match  {
            case Right(updatedBoard) =>
              println("Zug erfolgreich! Aktuelles Spielfeld:")
              // println(updatedBoard)
              nextPlayer() // Spieler wechseln
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
            nextPlayer() // Spieler wechseln
          case Left(errorMessage) =>
            println(s"Fehler: $errorMessage")
        }
      } catch {
        case _: Exception =>
          println("Ungültige Eingabe. Bitte im Format Zeile,Spalte eingeben.")
      }
    }
  }
}
