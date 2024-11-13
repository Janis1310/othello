package de.htwg.se.othello

import de.htwg.se.othello.aview.TUI
import de.htwg.se.othello.model.Board
import de.htwg.se.othello.controller.Controller

object Othello {
  val controller = new Controller(new Board(8, 8))
  val tui = new TUI(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var players = tui.inputPlayerName()
    val (row, column) = tui.inputBoardSize()
    var board = new Board(row, column)

    controller.board = board

    println("Das Spiel beginnt! Drücken Sie Strg + C, um das Spiel zu beenden.")
    while (true){
      val currentPlayer = players.head
      println(board)

      println(s"${currentPlayer.name}, du bist am Zug! Deine Farbe ist: ${currentPlayer.stone}")

      board = tui.playTurn(board, currentPlayer = currentPlayer )
      players = players.tail.enqueue(currentPlayer)

    }
  }
}
