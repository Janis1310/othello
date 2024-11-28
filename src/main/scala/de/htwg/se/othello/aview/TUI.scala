package de.htwg.se.othello.aview

import de.htwg.se.othello.model.{Stone, Board}
import scala.io.StdIn.readLine
import scala.collection.immutable.Queue

import de.htwg.se.othello.controller.Controller
import de.htwg.se.othello.util.Observer

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

  /*def playTurn(): Unit = {
    val currentPlayer = controller.getCurrentPlayer
    println(s"${currentPlayer.name}, du bist am Zug. Deine Farbe ist ${currentPlayer.stone}.")

    try {
      println("Gib die Koordinaten für deinen Zug im Format Zeile,Spalte ein:")
      val Array(x, y) = readLine().split(",").map(_.trim.toInt)
      controller.makeMove(x, y) match {
        case Right(updatedBoard) =>
          println("Zug erfolgreich! Aktuelles Spielfeld:")
          println(updatedBoard)
          controller.nextPlayer() // Spieler wechseln
        case Left(errorMessage) =>
          println(s"Fehler: $errorMessage")
      }
    } catch {
      case _: Exception =>
        println("Ungültige Eingabe. Bitte im Format Zeile,Spalte eingeben.")
    }
  }*/
    def playTurn(): Unit = {
    controller.processTurn() // Nutze die Methode, die entscheidet, ob der Spieler oder die KI am Zug ist
  }

  override def update: Unit = {
    println("Das Spielfeld wurde aktualisiert.")
    println(controller.boardToString)
  }
}
