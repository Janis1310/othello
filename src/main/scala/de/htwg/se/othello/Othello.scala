package de.htwg.se.othello

import de.htwg.se.othello.aview.GUI.OthelloGUI
import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl.Controller
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board
import com.google.inject.Guice
import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl

import de.htwg.se.othello.aview.TUI
import de.htwg.se.othello.controller.ControllerComponents.ControllerComponent
import scala.io.StdIn
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

object Othello {
  val injector = Guice.createInjector(new OthelloModule)
  val controller = injector.getInstance(classOf[ControllerComponent])
  val tui = TUI(controller)
  val gui = OthelloGUI(controller)

  def main(args: Array[String]): Unit = {
       val guiFuture = Future {
        gui.start()
      }
        val tuiFuture = Future {
          var input = ""
          while (input != "q") {
            tui.start
            input = StdIn.readLine()
            tui.processInputLine(input)
          }
        }
        Await.result(guiFuture, Duration.Inf)
        Await.result(tuiFuture, Duration.Inf)
  }
}