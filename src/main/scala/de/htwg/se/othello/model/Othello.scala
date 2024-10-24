package de.htwg.se.othello.model

object Othello {
  def main(args: Array[String]): Unit = {
    val board = new Board(8, 8) 
    println(board)
  }
}
