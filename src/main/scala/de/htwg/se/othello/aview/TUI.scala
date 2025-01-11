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

  def inputPlayers(): Unit = {
    println("Geben Sie den Namen des ersten Spielers ein:")
    val player1Name = StdIn.readLine()
    println("Geben Sie den Namen des zweiten Spielers ein:")
    val player2Name = StdIn.readLine()

    controller.addPlayers(player1Name, player2Name)
    val players = controller.getPlayers
    println(s"Spieler wurden hinzugefügt: ${players.head}, ${players.last}")
  }

  def inputBoardSize(): Unit = {
    println("Geben Sie die Größe des Spielfelds ein (Zeilen, Spalten):")

    val result = Try(StdIn.readLine().split(",").map(_.trim.toInt)) match {
      case Success(Array(rows, cols)) =>
        controller.createNewBoard(rows, cols)
        println(
          s"Ein neues Spielfeld mit $rows Zeilen und $cols Spalten wurde erstellt."
        )
      case Success(_) =>
        println("Ungültige Eingabe. Bitte geben Sie genau zwei Zahlen ein.")
        inputBoardSize()
      case Failure(_: NumberFormatException) =>
        println("Ungültige Eingabe. Bitte geben Sie gültige ganze Zahlen ein.")
        inputBoardSize()
      case Failure(e) =>
        println(s"Ein unerwarteter Fehler ist aufgetreten: ${e.getMessage}")
        inputBoardSize()
    }
  }

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "n" => {

        inputPlayers()
        inputBoardSize()
        controller.changeState(GameState.WHITE_TURN)
      }
      case "z" => controller.undo
      case "y" => controller.redo
      case _ =>
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

    }
  }
  override def update: Unit = {
    println("Das Spielfeld wurde aktualisiert.")
    println(controller.boardToString)
    println(GameState.message(controller.getGameState))
  }

  def start: Unit = {
    Future {
      println("Das Spiel beginnt!")
      var input: String = "n"
      while (input != "q")
      {

        if (controller.getGameState != GameState.SETUP)
          println("q => quit, z => undo, y => redo, n => new game")
        processInputLine(input)
        input = StdIn.readLine("Geben Sie die Koordinaten in Zeile,Spalte: ")
        println(controller.getCurrentPlayer.name + " ist am Zug. Deine Farbe ist " + controller.getCurrentPlayer.stone)
      }
      println("Das Spiel wurde beendet.")
    }
  }
}
