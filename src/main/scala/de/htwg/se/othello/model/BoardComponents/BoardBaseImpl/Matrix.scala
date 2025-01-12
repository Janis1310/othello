package de.htwg.se.othello.model.BoardComponents.BoardBaseImpl

import de.htwg.se.othello.model.BoardComponents.MatrixInterface
import com.google.inject.Inject
import de.htwg.se.othello.Othello.injector

case class Matrix[T](matrix: Vector[Vector[T]]) extends MatrixInterface[T] {
  val numRows: Int = matrix.size
  val numCols: Int =
    if (numRows > 0) matrix(0).size else 0 // Handle empty matrix case

  @Inject
  def this() = this(Vector.empty[Vector[T]])

  def this(row: Int, column: Int, filling: T) =
    this(Vector.tabulate(row, column) { (_, _) =>
      filling
    })

  // Retrieve the cell at the specified row and column
  override def cell(row: Int, col: Int): T = matrix(row)(col)

  // Return a new matrix with the specified cell replaced
  override def replaceCell(row: Int, col: Int, cell: T): Matrix[T] =
    copy(matrix.updated(row, matrix(row).updated(col, cell)))

  // Return a new matrix with all cells filled with the specified value
  override def fill(filling: T): Matrix[T] =
    copy(Vector.tabulate(numRows, numCols) { (_, _) => filling })
}
