package de.htwg.se.othello

import de.htwg.se.othello.aview.GUI.OthelloGUI
import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl.Controller
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board
import com.google.inject.Guice
import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl

import de.htwg.se.othello.aview.OthelloTUI
import de.htwg.se.othello.controller.ControllerComponents.ControllerComponent
import scala.io.StdIn
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Othello {
  val injector = Guice.createInjector(new OthelloModule)
  val controller = injector.getInstance(classOf[ControllerComponent])
  val tui = OthelloTUI(controller)
  val gui = OthelloGUI(controller)

  def main(args: Array[String]): Unit = {
    Future{gui.start()}

    Future {
    var input = ""
    
    while (input != "q") {
      tui.start
      input = StdIn.readLine()
      tui.processInputLine(input)
    }
  }
    
  }
}