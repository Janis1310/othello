
package de.htwg.se.othello.model

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.othello.model.{Matrix, Stone}

class MoveHandlerSpec extends AnyWordSpec {
    "A Move " when {
        "isValidMove" should {
            "be false when field is full" in {
                val board = new Board(8,8)
                board.isValidMove(3,3,Stone.Black) should be(false)
            }
            "be false when field is out of bounds" in {
                val board = new Board(8,8)
                board.isValidMove(9,9,Stone.Black) should be(false)
            }
            

        }
        "checkDirection" should {
            "be false when direction is out of bounds" in {
                val board = new Board(8,8)
                board.checkDirection(9,9,Stone.Black,1,1) should be(false)

        }
    }
}
}
