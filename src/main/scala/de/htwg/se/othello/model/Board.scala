package de.htwg.se.othello.model

class Board(private val board: Matrix[Stone]) {

    def this(row: Int, column: Int) = {
      this({
        val emptyBoard = new Matrix[Stone](row, column, Stone.Empty)

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

    def getBoard: Matrix[Stone] = board
    def numRows: Int = board.numRows
    def numCols: Int = board.numCols

    def placeStone(x: Int, y: Int, stone: Stone): Board = {
      val newMatrix = board.replaceCell(x, y, stone)
      new Board(newMatrix)
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

    def copy(): Board = {
    new Board(board.copy()) // Nutzt die copy-Methode der Matrix
  }
}