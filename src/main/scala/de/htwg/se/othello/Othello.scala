package de.htwg.se.othello

import de.htwg.se.othello.aview.{TUI, Gui}
import de.htwg.se.othello.model.Board
import de.htwg.se.othello.controller.Controller
import scala.util.{Try, Success, Failure}

import scala.io.StdIn.readLine

object Othello {
  val controller = new Controller(new Board(8, 8))
  val tui = new TUI(controller)
  //val gui = new Gui(controller)

  def main(args: Array[String]): Unit = {

    tui.start()
    Gui.main(args)
  }
}
