package de.htwg.se.othello.controller
import de.htwg.se.othello.model.{Board, Stone, Stoneposition, MoveHandler, Player}
import de.htwg.se.othello.util.Observable
import scala.collection.immutable.Queue


class Controller(private var board: Board) extends Observable {
  private var players: Queue[Player] = Queue()

  // Gibt das Board als Zeichenkette zurück
  def boardToString: String = board.toString

  // Spieler hinzufügen und den aktuellen Zustand zurückgeben
  def addPlayers(player1Name: String,player2Name: String): Unit = {
    players = Queue(new Player(player1Name, Stone.White), new Player(player2Name, Stone.Black))
  }

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
}
