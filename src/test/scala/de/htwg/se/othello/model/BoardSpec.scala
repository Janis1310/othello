package de.htwg.se.othello.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import de.htwg.se.othello.model.{Board, Stone} // import board and stone
import de.htwg.se.othello.model.Stone.BlackStone

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

    "place a stone correctly on the board" in {
      // Erstelle ein neues Board-Objekt mit einer Standardgröße (z.B. 8x8)
      val board = new Board(8, 8)

      // Platziere einen Stein (z.B., Stone.Black) an Position (3, 3)
      val updatedBoard = board.placeStone(3, 2, Stone.Black)

      // Überprüfe, dass der Stein an der richtigen Position platziert wurde
      updatedBoard.getBoard.cell(3, 2) shouldEqual Stone.Black

      // Überprüfe, dass das ursprüngliche Board unverändert ist
      board.getBoard.cell(3, 2) shouldEqual Stone.Empty
    }

    "return a new Board instance after placing a stone" in {
      // Erstelle ein neues Board-Objekt
      val board = new Board(8, 8)

      // Platziere einen Stein und erhalte das aktualisierte Board
      val updatedBoard = board.placeStone(2, 2, Stone.White)

      // Überprüfe, dass ein neues Board-Objekt zurückgegeben wird
      updatedBoard should not be theSameInstanceAs(board)
    }

    "return row and colum size of the board" in {
      val board = new Board(6,8)

      board.numRows shouldBe (6)

      board.numCols shouldBe (8)
    }

     "correctly copy the board" in {
      val originalBoard = new Board(8, 8)

      // Erstelle eine Kopie des Boards
      val copiedBoard = originalBoard.copy().asInstanceOf[Board]

      // Stelle sicher, dass das kopierte Board eine andere Instanz ist
      copiedBoard should not be theSameInstanceAs(originalBoard)

      // Überprüfe, dass die Matrix des kopierten Boards eine andere Instanz ist
      copiedBoard.getBoard should not be theSameInstanceAs(originalBoard.getBoard)

      // Stelle sicher, dass die Daten der Matrix im kopierten Board gleich sind
      copiedBoard.getBoard.cell(3,3) should equal(originalBoard.getBoard.cell(3,3))

    }
}
}