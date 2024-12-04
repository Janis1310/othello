package de.htwg.se.othello

import de.htwg.se.othello.aview.TUI
import de.htwg.se.othello.model.Board
import de.htwg.se.othello.controller.Controller

import scala.io.StdIn.readLine

object Othello {
  val controller = new Controller(new Board(8, 8))
  val tui = new TUI(controller)

  def main(args: Array[String]): Unit = {
    tui.inputPlayers()
    tui.inputBoardSize()

    println("Das Spiel beginnt! Drücken Sie Strg + C, um das Spiel zu beenden.")
    var input: String = ""
    if (args.length>0) input=args(0)
    if (!input.isEmpty)  tui.processInputLine(input)
    else {
      input = readLine()
      while (input != "q") {
        tui.processInputLine(input)
        input = readLine()
      }
    } 
  }
}
