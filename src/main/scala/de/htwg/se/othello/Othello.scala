package de.htwg.se.othello

import de.htwg.se.othello.aview.TUI

import scala.io.StdIn.readLine
import de.htwg.se.othello.aview.GUI.OthelloGUI
import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl.Controller
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board
import com.google.inject.Guice
import de.htwg.se.othello.controller.ControllerComponents.ControllerComponent

object Othello {
  val injector = Guice.createInjector(new OthelloModule)
  val controller = injector.getInstance(classOf[ControllerComponent])
  val tui = TUI(controller)
  val gui = OthelloGUI(controller)

  def main(args: Array[String]): Unit = {
    gui.start()
    tui.start
    // tui.inputPlayers()
    // tui.inputBoardSize()
    // println("Das Spiel beginnt!")
    // var input: String = ""
    // while (input != "q") {
    //   println(controller.getCurrentPlayer.name + " ist am Zug. Deine Farbe ist " + controller.getCurrentPlayer.stone)
    //   println("q => quit, z => undo, y => redo, n => new game")
    //   input = readLine("Geben Sie die Koordinaten in Zeile,Spalte: ")
    //   tui.processInputLine(input)  // Eingabe verarbeiten
    // }
    // println("Das Spiel wurde beendet.")
  }
}
