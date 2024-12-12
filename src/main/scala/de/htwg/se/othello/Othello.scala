package de.htwg.se.othello

import de.htwg.se.othello.aview.TUI
import de.htwg.se.othello.model.Board
import de.htwg.se.othello.controller.Controller
import scala.util.{Try, Success, Failure}

import scala.io.StdIn.readLine
import de.htwg.se.othello.aview.GUI.OthelloGUI

object Othello {
  val controller = new Controller(new Board(8, 8))
  val tui = new TUI(controller)
  val gui = new OthelloGUI(controller)

  def main(args: Array[String]): Unit = {
    //gui.start()
    tui.inputPlayers()
    tui.inputBoardSize()
    println("Das Spiel beginnt!")
    var input: String = ""
    while (input != "q") {
      println(controller.getCurrentPlayer.name + " ist am Zug. Deine Farbe ist " + controller.getCurrentPlayer.stone)
      println("q => quit, z => undo, y => redo, n => new game")
      input = readLine("Geben Sie die Koordinaten in Zeile,Spalte: ")
      tui.processInputLine(input)  // Eingabe verarbeiten
    }

    println("Das Spiel wurde beendet.")
  }
}
