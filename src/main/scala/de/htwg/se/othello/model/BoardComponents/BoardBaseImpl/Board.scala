package de.htwg.se.othello.model.BoardComponents.BoardBaseImpl

import de.htwg.se.othello.model.BoardComponents.{BoardComponent, MatrixInterface, StoneComponent}
import com.google.inject.Inject
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone.Stone

class Board @Inject() (private val board: MatrixInterface[StoneComponent])
    extends BoardComponent {

  def this(row: Int, column: Int) = {
    this({
      val emptyBoard = new Matrix[StoneComponent](row, column, Stone.Empty)

      // Calculate center positions for the initial stones
      val pos1 = (row - 1) / 2
      val pos2 = (column - 1) / 2

      // Place the initial stones on the board
      emptyBoard
        .replaceCell(pos1, pos2, Stone.White)
        .replaceCell(pos1, pos2 + 1, Stone.Black)
        .replaceCell(pos1 + 1, pos2, Stone.Black)
        .replaceCell(pos1 + 1, pos2 + 1, Stone.White)
    })
  }

  def getBoard: MatrixInterface[StoneComponent] = board
  def numRows: Int = board.numRows
  def numCols: Int = board.numCols

  def placeStone(x: Int, y: Int, stone: StoneComponent): BoardComponent = {
    val newMatrix = board.replaceCell(x, y, stone)
    Board(newMatrix)
  }
  def getStoneAt(x: Int, y: Int): Stone = {
    if (x < 0 || x >= board.numRows || y < 0 || y >= board.numCols) {
      throw new IllegalArgumentException(s"Position ($x, $y) liegt außerhalb des Boards.")
    }
    board.cell(x,y)
  }

  override def toString: String = {
    val numRows = board.numRows
    val numCols = board.numCols

    val sb = new StringBuilder
    sb.append("    ")
    for (i <- 0 until numCols) {
      sb.append(s"$i   ")
    }
    sb.append("\n")

    for (row <- 0 until numRows) {
      sb.append("  ")
      sb.append("+---" * numCols)
      sb.append("+\n")
      sb.append(s"$row | ")
      for (col <- 0 until numCols) {
        sb.append(s"${board.cell(row, col).toString} | ")
      }
      sb.append("\n")
    }
    sb.append("  ")
    sb.append("+---" * numCols)
    sb.append("+\n")

    sb.toString()
  }

  def copy(): BoardComponent = {
    board match {
      case m: Matrix[StoneComponent] =>
        new Board(m.copy()) // Matrix.copy() wird hier verwendet
      case _ =>
        throw new UnsupportedOperationException(
          "Unsupported Matrix type for copy"
        )
    }
  }
}
