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

  //val gui = new Gui(controller)

  def main(args: Array[String]): Unit = {
    // GuiMain.setController(controller)
    // GuiMain.main(args)
    // tui.start()
    // GuiMain.main(args)
    gui.start()

  }
}
