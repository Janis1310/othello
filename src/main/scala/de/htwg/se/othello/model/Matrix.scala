package de.htwg.se.othello.model

case class Matrix[T](rows: Vector[Vector[T]]) {
  
  // Alternate constructor to create a filled matrix of a specific size
  def this(size: Int, filling: T) = this(Vector.tabulate(size, size)((_, _) => filling))

  val size: Int = rows.size

  // Method to access a specific cell
  def cell(row: Int, col: Int): T = rows(row)(col)

  // Method to fill the matrix with a specific value
  def fill(filling: T): Matrix[T] = copy(Vector.tabulate(size, size)((_, _) => filling))

  // Method to replace a specific cell
  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] =
    copy(rows.updated(row, rows(row).updated(col, cell)))
}

