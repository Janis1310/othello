package de.htwg.se.othello.model

object Othello {
  def main(args: Array[String]): Unit = {

val stonePosition1 = Stoneposition(2, 4, Stone.White) // Beispielposition und Steinfarbe
val board = new Board(8, 8) // Beispiel für ein 8x8 Othello-Board

// Erhalte die Liste der Zwischenzustände
val setStone1 = MoveHandler.flipStones(stonePosition1, board)
print(setStone1)

val stonePosition2 = Stoneposition(4,2, Stone.White)

val setStone2 = MoveHandler.flipStones(stonePosition2, setStone1)
print(setStone2)





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
