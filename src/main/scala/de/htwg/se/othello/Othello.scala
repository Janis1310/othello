package de.htwg.se.othello

import scala.io.StdIn._

object Othello {
  def main(args:Array[String]) = {
    println("Welcome to Othello ")

    val p1_name = readLine("Geben Sie den Name vom Player1 ein: ")
    val p2_name = readLine("Geben Sie den Name vom Player2 ein: ")
    val p1 = Player(p1_name, "W")
    val p2 = Player(p2_name, "S")

    val feld = readLine("Geben Sie die Feldgröße getrennt durch ein Komma ein: ")
    val Array(row, column) = feld.split(",").map(_.trim.toInt)

    val board = createboard(row, column)
    printboard(board)


  }

  def createboard(row:Int, column:Int): Array[Array[String]] = {
    val board = Array.fill(row, column)(".")
    val pos1 = (row - 1) / 2
    val pos2 = ( column - 1) / 2

    board(pos1)(pos2) = "W"
    board(pos1)(pos2+1) = "B"
    board(pos1+1)(pos2) = "B" 
    board(pos1+1)(pos2+1) = "W"
    board
  
  }

  // print the board
  def printboard(board: Array[Array[String]]) : Unit = {
    val boardmaxcol = board(0).length
    val boardmaxrow = board.indices

    print("    ")
    board(0).indices.foreach(i => print(s"$i   "))
    println()
    
    for (row <- boardmaxrow) {
      print("  ")

      print("+---" * boardmaxcol)
      println("+")
      print(s"$row | ") 
      for (col <- board(row).indices) {
        print(s"${board(row)(col)} | ") 
      }
      println()
    }
    print("  ")
    print("+---" * boardmaxcol)
    println("+")

  }


}

case class Player(name:String, color:String){
    override def toString(): String = name +": " + color

}


