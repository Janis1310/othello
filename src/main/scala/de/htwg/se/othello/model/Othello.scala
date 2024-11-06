package de.htwg.se.othello.model

object Othello {
  def main(args: Array[String]): Unit = {

val stonePosition = Stoneposition(2, 4, Stone.White) // Beispielposition und Steinfarbe
val board = new Board(8, 8) // Beispiel für ein 8x8 Othello-Board

// Erhalte die Liste der Zwischenzustände
val flipSteps = MoveHandler.flipStones(stonePosition, board)
print(flipSteps)




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
