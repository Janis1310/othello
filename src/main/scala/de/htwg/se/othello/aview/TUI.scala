package de.htwg.se.othello.aview

import de.htwg.se.othello.model.{Stone, Board}
import scala.io.StdIn.readLine
import scala.collection.immutable.Queue

import de.htwg.se.othello.controller.{GameState, Controller}
import de.htwg.se.othello.util.Observer
import scala.util.{Try, Success, Failure}

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def inputPlayers(): Unit = {
    println("Geben Sie den Namen des ersten Spielers ein:")
    val player1Name = readLine()
    println("Geben Sie den Namen des zweiten Spielers ein:")
    val player2Name = readLine()

    controller.addPlayers(player1Name, player2Name)
    val players = controller.getPlayers
    println(s"Spieler wurden hinzugefügt: ${players.head}, ${players.last}")
  }

  def inputBoardSize(): Unit = {
    try {
      println("Geben Sie die Größe des Spielfelds ein (Zeilen, Spalten):")
      val Array(rows, cols) = readLine().split(",").map(_.trim.toInt)
      controller.createNewBoard(rows, cols)
      println(s"Ein neues Spielfeld mit $rows Zeilen und $cols Spalten wurde erstellt.")
    } catch {
      case _: Exception =>
        println("Ungültige Eingabe. Bitte geben Sie zwei ganze Zahlen getrennt durch ein Komma ein.")
        inputBoardSize() // Wiederholung bei Fehler
    }
  }
  def processInputLine(input: String):Unit = {
    input match {
      case "q" =>
      case "n"=> controller.createNewBoard(8,8)
      case "z" => controller.undo
      case "y" => controller.redo
      case _ =>       
       /* try {
          val Array(x, y) = input.split(",").map(_.trim.toInt)
          controller.processTurn(x, y)
        } catch {
          case _: MatchError =>
            println("Eingabe muss im Format 'x,y' sein.")
          case _: NumberFormatException =>
            println("Eingabe enthält ungültige Zahlen.")
        }*/
        
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

  def start(): Unit = {
    inputPlayers()
    inputBoardSize()
    println("Das Spiel beginnt! Drücken Sie Strg + C, um das Spiel zu beenden.")
    var input: String = ""
    while (input != "q") {  // Schleife weiter ausführen, bis "q" eingegeben wird
    println(controller.getCurrentPlayer.name + ", ist im Zug, Deine Farbe ist " + controller.getCurrentPlayer.stone);
      println("q => quit, z => undo, y => redo")
      
      // Lese die Benutzereingabe
      input = readLine("Geben Sie die Koordinaten in Zeile,Spalte: ")  // Hier wird die Eingabe des Benutzers abgefragt

      // Verarbeite die Eingabe
      processInputLine(input)  // Eingabe verarbeiten
    }

    println("Das Spiel wurde beendet.")
  }
}
