
package de.htwg.se.othello.model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.othello.model.{Matrix, Stone}

class MoveHandlerSpec extends AnyWordSpec {
    "A Move" when {
        "isValidMove" should {
            "be false when field is out of bounds" in {
                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                MoveHandler.isValidMove(Stoneposition(9, 9, Stone.Black), board) should be(false)
            }
            "be false when trying to place a stone on an already occupied field" in {
                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                MoveHandler.isValidMove(Stoneposition(3, 3, Stone.Black), board) should be(false)
            }
            "be false when trying to place a stone on the edge of the board" in {
                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                MoveHandler.isValidMove(Stoneposition(0, 0, Stone.Black), board) should be(false)
            }
            "be true for a valid move in the top-left direction" in {
                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                MoveHandler.isValidMove(Stoneposition(2, 4, Stone.White), board) should be(true)
            }
            "be true for a valid move in the bottom-right direction" in {
                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                MoveHandler.isValidMove(Stoneposition(5, 3, Stone.White), board) should be(true)
            }
        }

        "flipStones" should {
            "flip stones horizontally to the left" in {
                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                val newBoard = MoveHandler.flipStones(Stoneposition(3, 2, Stone.Black), board)
                newBoard.getBoard.cell(3, 2) should be(Stone.Black) // neuer Stein
                newBoard.getBoard.cell(3, 3) should be(Stone.Black) // umgedrehter Stein
            }
            "flip stones vertically upward" in {
                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                val newBoard = MoveHandler.flipStones(Stoneposition(2, 4, Stone.Black), board)
                newBoard.getBoard.cell(2, 4) should be(Stone.Black) // neuer Stein
                newBoard.getBoard.cell(3, 4) should be(Stone.Black) // umgedrehter Stein
            }
            "flip stones diagonally bottom-right" in {
                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                val newBoard = MoveHandler.flipStones(Stoneposition(5, 3, Stone.White), board)
                newBoard.getBoard.cell(5, 3) should be(Stone.White) // neuer Stein
                newBoard.getBoard.cell(4, 3) should be(Stone.White) // umgedrehter Stein
            }
            "not flip any stones for an invalid move" in {

                val board = new Board(8, 8) // Startzustand wird automatisch initialisiert
                val newBoard = MoveHandler.flipStones(Stoneposition(0, 0, Stone.Black), board)
                newBoard.getBoard.cell(0, 0) should be(Stone.Black) // Stein wird trotzdem gesetzt
                newBoard.getBoard.cell(3, 4) should be(Stone.Black) // ursprünglicher Zustand bleibt unverändert
                newBoard.getBoard.cell(4, 3) should be(Stone.Black) // ursprünglicher Zustand bleibt unverändert
            }
        }
    }
}

