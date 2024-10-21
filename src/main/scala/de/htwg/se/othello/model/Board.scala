package de.htwg.se.othello.model

class Board(private val board: Vector[Vector[Stone]]) {

    def this(row: Int, column: Int) = {
    this(Vector.fill(row, column)(Stone.Empty))
    val pos1 = (row - 1) / 2
    val pos2 = ( column - 1) / 2

    val new_board = board
      .updated(pos1, board(pos1).updated(pos2, Stone.White)) // Platziere den ersten weißen Stein
      .updated(pos1, board(pos1).updated(pos2 + 1, Stone.Black)) // Platziere den ersten schwarzen Stein
      .updated(pos1 + 1, board(pos1 + 1).updated(pos2, Stone.Black)) // Platziere den zweiten schwarzen Stein
      .updated(pos1 + 1, board(pos1 + 1).updated(pos2 + 1, Stone.White)) // Platziere den zweiten weißen Stein
    
  }
    override def toString: String = {
        val boardmaxcol = board(0).length
        val boardmaxrow = board.indices

        val sb = new StringBuilder
        sb.append("    ") 
        for (i <- 0 until boardmaxcol) {
          sb.append(s"$i   ")
        }
        sb.append("\n")

        for (row <- boardmaxrow) {
          sb.append("  ")
          sb.append("+---" * boardmaxcol)
          sb.append("+\n")
          sb.append(s"$row | ") 
          for (col <- board(row).indices) {
            sb.append(s"${board(row)(col).toString} | ")
          }
          sb.append("\n")
        }
        sb.append("  ")
        sb.append("+---" * boardmaxcol) 
        sb.append("+\n")

        sb.toString()
      }
}
