package de.htwg.se.othello.aview

import de.htwg.se.othello.model.{Player, Stone, Board, MoveHandler, Stoneposition}
import scala.io.StdIn.readLine
import scala.collection.immutable.Queue
import de.htwg.se.othello.model.Player

class TUI {

  def inputPlayerName(): Queue[Player] = {
    val player1 = Player(readPlayerName(1), Stone.White)
    val player2 = Player(readPlayerName(2), Stone.Black)
    Queue(player1, player2) // Rückgabe des erstellten Queue
  }

  def inputBoardSize(): (Int, Int) = {
    val Array(row, column) = readLine("Geben Sie die Größe des Feldes Zeilen,Spalten: ").split(",").map(_.trim.toInt)
    (row, column) 
  }

    private def readPlayerName(playerNumber: Int): String = {
    println(s"Geben Sie den Namen des Spielers $playerNumber ein:")
    readLine()
  }

  def playTurn(board: Board, currentPlayer: Player) : Board = {

    
    while(true) {
      val Array(x, y) = readLine("Gib die Koordinaten für deinen Zug im Format Zeile,Spalte ein: ").split(",").map(_.trim.toInt)
      if (MoveHandler.isValidMove(Stoneposition(x, y, currentPlayer.stone), board)) {
        return MoveHandler.flipStones(Stoneposition(x, y, currentPlayer.stone), board)
      } else {
        println("Ungültiger Zug. Bitte versuche es erneut.")
      }
    }
    return null // Unreachable code
  }
}
