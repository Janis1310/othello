package de.htwg.se.othello.aview

import de.htwg.se.othello.util.Observer
import scala.util.{Try, Success, Failure}
import de.htwg.se.othello.controller.ControllerComponents.{
  ControllerComponent,
  GameState
}
import scala.io.StdIn
import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.Inject

class TUI @Inject()(controller: ControllerComponent) extends Observer {
  controller.add(this)


  def processInputLine(input: String): Unit = {
    if (input == "q"){
      println("Spiel wird beendet."); System.exit(0)
    }
    controller.getGameState match {
    case GameState.SETUP =>
      input match {
        case "n" =>
          println("Neues Spiel gestartet!")
          controller.changeState(GameState.InputPlayer1)
        case _ => println("Ungültige Eingabe. 'n' für neues Spiel, 'q' zum Beenden.")
      }

    case GameState.InputPlayer1 | GameState.InputPlayer2 =>
      if (input.nonEmpty) {
        controller.addPlayers(input)
        if (controller.getPlayers.length >= 2) {
          controller.changeState(GameState.InputBoardSize) // Wechsel zu Spielfeldgrößen-Eingabe
        } 
      } else {
        println("Name darf nicht leer sein. Bitte erneut eingeben:")
      }

    case GameState.InputBoardSize =>
      val result = for {
    Array(rows, cols) <- Try(input.split(",").map(_.trim.toInt))} yield (rows, cols)

      result match {
      case Success((rows, cols)) if rows > 0 && cols > 0 =>
        controller.createNewBoard(rows, cols)
        println(s"Spiel gestartet mit ${controller.getPlayers.head} und ${controller.getPlayers.last} auf einem $rows x $cols Feld.")
        //controller.changeState(GameState.WHITE_TURN)
      case Success((_, _)) =>
        println("Die Breite und Länge müssen positive Zahlen sein. Bitte erneut eingeben:")
      case Failure(_: MatchError) =>
        println("Eingabe muss im Format 'x,y' sein. Bitte erneut eingeben:")
      case Failure(_: NumberFormatException) =>
        println("Eingabe enthält ungültige Zahlen. Bitte erneut eingeben:")
      case Failure(e) =>
        println(s"Ein unerwarteter Fehler ist aufgetreten: ${e.getMessage}")
    }


    case GameState.WHITE_TURN | GameState.BLACK_TURN =>
      val result = for {
        Array(x, y) <- Try(input.split(",").map(_.trim.toInt))
      } yield (x, y)

      result match {
        case Success((x, y)) =>
          controller.processTurn(x, y)
        case Failure(_: MatchError) =>
          println("Eingabe muss im Format 'x,y' sein.")
        case Failure(_: NumberFormatException) =>
          println("Eingabe enthält ungültige Zahlen.")
        case Failure(e) =>
          println(s"Ein unerwarteter Fehler ist aufgetreten: ${e.getMessage}")
      }

    case _ => println("Unbekannter Zustand. Bitte Spiel neu starten.")
  }


  }
  override def update: Unit = {
    println("Das Spielfeld wurde aktualisiert.")
    println(controller.boardToString)
    if (controller.getGameState == GameState.BLACK_TURN || controller.getGameState == GameState.WHITE_TURN) {
      println("q => quit, z => undo, y => redo")
      println(s"${controller.getCurrentPlayer.name}, Du bist dran. Dein Stein ist: ")
      println("Geben Sie die Koordinaten des Steins ein (Format: Zeile,Spalten: )")
    }
  }

  def start: Unit = {
    if (controller.getGameState == GameState.SETUP){
      println("Willkommen zu Othello!")
      println("q => quit, n => new game")

    }
  }
}
