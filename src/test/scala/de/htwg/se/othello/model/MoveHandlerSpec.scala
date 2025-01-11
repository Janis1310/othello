package de.htwg.se.othello.model

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{
  Board,
  Matrix,
  Stone,
  Stoneposition
}
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandler
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MoveHandlerSpec extends AnyWordSpec {
  "A Move" when {
    "isValidMove" should {
      "be false when field is out of bounds" in {
        val board =
          new Board(8, 8) // Startzustand wird automatisch initialisiert
        MoveHandler.isValidMove(
          Stoneposition(9, 9, Stone.Black),
          board
        ) should be(false)
      }
      "be false when trying to place a stone on an already occupied field" in {
        val board =
          new Board(8, 8) // Startzustand wird automatisch initialisiert
        MoveHandler.isValidMove(
          Stoneposition(3, 3, Stone.Black),
          board
        ) should be(false)
      }
      "be false when trying to place a stone on the edge of the board" in {
        val board =
          new Board(8, 8) // Startzustand wird automatisch initialisiert
        MoveHandler.isValidMove(
          Stoneposition(0, 0, Stone.Black),
          board
        ) should be(false)
      }
      "be true for a valid move in the top-left direction" in {
        val board =
          new Board(8, 8) // Startzustand wird automatisch initialisiert
        MoveHandler.isValidMove(
          Stoneposition(2, 4, Stone.White),
          board
        ) should be(true)
      }
      "be true for a valid move in the bottom-right direction" in {
        val board =
          new Board(8, 8) // Startzustand wird automatisch initialisiert
        MoveHandler.isValidMove(
          Stoneposition(5, 3, Stone.White),
          board
        ) should be(true)
      }
      "be false when trying to place a stone next to the same stone" in {
        val board =
          new Board(8, 8) // Startzustand wird automatisch initialisiert
        MoveHandler.isValidMove(
          Stoneposition(3, 5, Stone.Black),
          board
        ) should be(false)
      }
    }

    "flipStones" should {
      "flip stones horizontally to the left" in {
        val board =
          new Board(8, 8)
        val newBoard =
          MoveHandler.processMove(Stoneposition(3, 2, Stone.Black), board)
        newBoard.getBoard.cell(3, 2) should be(Stone.Black)
        newBoard.getBoard.cell(3, 3) should be(Stone.Black)
      }
      "flip stones vertically upward" in {
        val board =
          new Board(8, 8)
        val newBoard =
          MoveHandler.processMove(Stoneposition(2, 4, Stone.White), board)
        newBoard.getBoard.cell(2, 4) should be(Stone.White)
        newBoard.getBoard.cell(3, 4) should be(Stone.White)
      }
      "flip stones diagonally bottom-right" in {
        val board =
          new Board(8, 8)
        val newBoard =
          MoveHandler.processMove(Stoneposition(5, 3, Stone.White), board)
        newBoard.getBoard.cell(5, 3) should be(Stone.White)
        newBoard.getBoard.cell(4, 3) should be(Stone.White)
      }
      "not flip any stones for an invalid move" in {
        val board = new Board(8, 8)
        val invalidMove = Stoneposition(0, 0, Stone.White)
        an[IllegalArgumentException] shouldBe thrownBy {
          MoveHandler.processMove(invalidMove, board)
        }
      }
    }
  }
}
