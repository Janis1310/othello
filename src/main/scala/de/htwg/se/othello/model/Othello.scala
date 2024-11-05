package de.htwg.se.othello.model

object Othello {
  def main(args: Array[String]): Unit = {

    val board = new Board(8, 8)
    println(board)

    val stonePosition = Stoneposition(3, 5, Stone.White) // Beispielposition
    val isValid = moveValidator.isValidMove(stonePosition, board)

    println(s"Is the move valid? $isValid")




    //Test: isValidMove
  //   if (moveValidator.isValidMove(2, 4, Stone.White, board)) {
  //     println("Move is valid")
  //     val newBoard = board.updated(2, 4, Stone.White)
  //     println(newBoard)
  //   } else {
  //     println("Move is not valid")
  //   }
  // }
  }
}
