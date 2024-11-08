
package de.htwg.se.othello.model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.othello.model.{Matrix, Stone}

class MoveHandlerSpec extends AnyWordSpec {
    "A Move " when {
        "isValidMove" should {
            "be false when field is full" in {
                val board = new Board(8,8)
                MoveHandler.isValidMove(Stoneposition(3,3,Stone.Black), board) should be(false)
            }
            "be false when field is out of bounds" in {
                val board = new Board(8,8)
                MoveHandler.isValidMove(Stoneposition(9,9,Stone.Black), board) should be(false)
            }
        }
        "flipStones" should {
            "flip stonens left direction" in {
                val board = new Board(8,8)
                val newBoard = MoveHandler.flipStones(Stoneposition(3,2,Stone.Black), board)
                newBoard.getBoard.cell(3,2) should be(Stone.Black) //new stone
                newBoard.getBoard.cell(3,3) should be(Stone.Black) //fliped stone
            }
            "flip stonens right direction" in {
                val board = new Board(8,8)
                val newBoard = MoveHandler.flipStones(Stoneposition(3,5,Stone.White), board)
                newBoard.getBoard.cell(3,5) should be(Stone.White) //new stone
                newBoard.getBoard.cell(3,4) should be(Stone.White) //fliped stone
            }
            "flip stonens up direction" in {
                val board = new Board(8,8)
                val newBoard = MoveHandler.flipStones(Stoneposition(2,4,Stone.White), board)
                newBoard.getBoard.cell(2,4) should be(Stone.White) //new stone
                newBoard.getBoard.cell(3,4) should be(Stone.White) //fliped stone
            }
            "flip stonens down direction" in {
                val board = new Board(8,8)
                val newBoard = MoveHandler.flipStones(Stoneposition(5,4,Stone.Black), board)
                newBoard.getBoard.cell(5,4) should be(Stone.Black) //new stone
                newBoard.getBoard.cell(4,4) should be(Stone.Black) //fliped stone
            }
            "flip stonens right up direction" in {
                val board = new Board(8,8)
                val newboard = MoveHandler.flipStones(Stoneposition(2,4,Stone.White), board)
                val newBoard1 = MoveHandler.flipStones(Stoneposition(2,5,Stone.Black), newboard)
                newBoard1.getBoard.cell(2,5) should be(Stone.Black) //new stone
                newBoard1.getBoard.cell(3,4) should be(Stone.Black) //double fliped stone
            }
            "stone on the edge" in {
                val board = new Board(8,8)
                val newBoard = MoveHandler.flipStones(Stoneposition(0,0,Stone.Black), board)
                newBoard.getBoard.cell(0,0) should be(Stone.Black) //new stone
                
            }
        }
    }
}
