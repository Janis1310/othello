package de.htwg.se.othello.model.BoardComponents

trait MatrixInterface[T] {
  
    def numRows: Int       // Number of rows
    def numCols: Int       // Number of columns

    // Method to get a cell value at a specific position
    def cell(row: Int, col: Int): T

    // Method to fill the matrix with a specific value
    def fill(filling: T): MatrixInterface[T]

    // Method to replace a specific cell
    def replaceCell(row: Int, col: Int, cell: T): MatrixInterface[T]
}
