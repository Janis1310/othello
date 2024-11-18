package de.htwg.se.othello

import de.htwg.se.othello.aview.TUI
import de.htwg.se.othello.model.Board
import de.htwg.se.othello.controller.Controller

object Othello {
  val controller = new Controller(new Board(8, 8))
  val tui = new TUI(controller)

  def main(args: Array[String]): Unit = {
    tui.inputPlayers()
    tui.inputBoardSize()

    println("Das Spiel beginnt! Drücken Sie Strg + C, um das Spiel zu beenden.")
    while (true) {
      tui.playTurn()
    }
  }
}
