package de.htwg.se.othello.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class OthelloSpec extends AnyWordSpec with Matchers {
  "The Othello object" should {
    "initialize a board with the correct default size and stones" in {
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

      board.toString().replaceAll("\\s+", "") shouldBe initialBoardStr.replaceAll("\\s+", "")
    }
  }
}