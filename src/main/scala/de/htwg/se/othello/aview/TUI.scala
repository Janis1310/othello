package de.htwg.se.othello.aview

import de.htwg.se.othello.model.{Stone, Board}
import scala.io.StdIn.readLine
import scala.collection.immutable.Queue
import de.htwg.se.othello.model.Player

import de.htwg.se.othello.controller.Controller
import de.htwg.se.othello.util.Observer

class TUI(controller: Controller) extends Observer {
  controller.add(this)
  def inputPlayerName(): Queue[Player] = {
    val player1 = controller.newPlayerController(readPlayerName(1), Stone.White)
    val player2 = controller.newPlayerController(readPlayerName(2), Stone.Black)
    Queue(player1, player2) // Rückgabe des erstellten Queue
  }

  def inputBoardSize(): (Int, Int) = {
    try {
      val Array(row, column) = readLine("Geben Sie die Größe des Feldes Zeilen,Spalten: ").split(",").map(_.trim.toInt)
      (row, column)
    } catch {
      case _: Exception =>
        println("Ungültige Eingabe. Bitte geben Sie zwei ganze Zahlen getrennt durch ein Komma ein.")
        inputBoardSize() // retry
    }
  }

    private def readPlayerName(playerNumber: Int): String = {
    println(s"Geben Sie den Namen des Spielers $playerNumber ein:")
    readLine()
  }

  def playTurn(board: Board, currentPlayer: Player): Board = { // Board muss hier noch raus!!!
    var validBoard: Option[Board] = None

    while (validBoard.isEmpty) {
      try {
        val Array(x, y) = readLine("Gib die Koordinaten für deinen Zug im Format Zeile,Spalte ein: ").split(",").map(_.trim.toInt)
        val position = controller.newStonePositionController(x, y, currentPlayer.stone)
        
        if (controller.isValidMoveController(position, board)) { // test
          validBoard = Some(controller.setNewStoneController(position, board)) // test
        } else {
          println("Ungültiger Zug. Bitte versuche es erneut.")
        }
      } catch {
        case _: Exception =>
          println("Ungültiges Format. Bitte geben Sie zwei ganze Zahlen im Format Zeile,Spalte ein.")
      }
    }
    validBoard.get // Rückgabe des gültigen Spielfelds
  }

  override def update: Unit = {
    println(controller.boardToString) // Print the updated board
  }
}
