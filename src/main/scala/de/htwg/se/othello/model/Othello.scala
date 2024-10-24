package de.htwg.se.othello.model

object Othello {
  def main(args: Array[String]): Unit = {
    val board = new Board(10, 10) 
    println(board)
  }
}
