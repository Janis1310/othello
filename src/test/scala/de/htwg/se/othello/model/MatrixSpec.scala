
package de.htwg.se.tictactoe.model

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Matrix, Stone}
import de.htwg.se.othello.model.BoardComponents.StoneComponent
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class MatrixSpec extends AnyWordSpec {
    "A Matrix" when {
        "created with specified dimensions" should {
            "correctly set the number of rows and columns" in {
                val matrix = new Matrix[StoneComponent](3, 4, Stone.Empty)
                matrix.numRows should be(3)
                matrix.numCols should be(4)
            }
        }
        "created with negative dimensions" should {
            "handle negative row or column values by setting numRows and numCols to 0" in {
                val negativeMatrix = new Matrix[StoneComponent](-1, 3, Stone.Empty)
                negativeMatrix.numRows should be(0)
                negativeMatrix.numCols should be(0)
            }
        }

        "empty created matrix" should {
            val matrix = new Matrix[StoneComponent](-1,3, Stone.Empty)

        }

        "created with empty stones" should {
            "give access to its cells" in {
                val matrix = new Matrix[StoneComponent](8, 8, Stone.Empty)
                matrix.cell(0, 0) should be(Stone.Empty)
            }
        }
        "create with Black.Stones" should {
            val matrix = new Matrix[StoneComponent](8,8, Stone.Black)
            "replace cells and return a new data structure" in {
                val returnedMatrix = matrix.replaceCell(0, 0, Stone.White)
                matrix.cell(0, 0) should be(Stone.Black)
                returnedMatrix.cell(0, 0) should be(Stone.White)
            }
            "be filled using fill operation" in {
                val returnedMatrix = matrix.fill(Stone.Empty)
                returnedMatrix.cell(0, 0) should be(Stone.Empty)
            }
        }
  }
}

