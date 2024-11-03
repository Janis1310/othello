package de.htwg.se.othello.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.othello.model.{Board, Stone} // import board and stone

class BoardSpec extends AnyWordSpec with Matchers {
  "A Board" should {

    "be initialized with the correct default size and stones" in {
      val board = new Board(8, 8)

      val initialBoardStr = """
            0   1   2   3   4   5   6   7
          +---+---+---+---+---+---+---+---+
        0 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        1 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        2 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        3 | . | . | . | W | S | . | . | . |
          +---+---+---+---+---+---+---+---+
        4 | . | . | . | S | W | . | . | . |
          +---+---+---+---+---+---+---+---+
        5 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        6 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        7 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+ """

        println(board.toString()) // Gibt die tatsächliche Ausgabe des Boards aus
        board.toString().replaceAll("\\s+", "") shouldBe initialBoardStr.replaceAll("\\s+", "")
    }
    
  }
}