package de.htwg.se.othello.model

import de.htwg.se.othello.aview.TUI

object Othello {
  def main(args: Array[String]): Unit = {

    val tui = new TUI
    var players = tui.inputPlayerName()
    val (row, column) = tui.inputBoardSize()
    var board = new Board(row, column)

    println("Das Spiel beginnt! Drücken Sie Strg + C, um das Spiel zu beenden.")

    while (true){
      val currentPlayer = players.head
      println(board)

      println(s"${currentPlayer.name}, du bist am Zug! Deine Farbe ist: ${currentPlayer.stone}")

      board = tui.playTurn(board, currentPlayer = currentPlayer )
      players = players.tail.enqueue(currentPlayer)

    }
    // var board = new Board(6,6)
    // println(board)
    // board = MoveHandler.flipStones(Stoneposition(1,3,Stone.White), board = board )
    // println(board)
    //  board = MoveHandler.flipStones(Stoneposition(3,4,Stone.Black), board = board )
    // println(board)


  }
}
