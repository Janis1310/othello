
trait MatrixInterface[T] {
  
    def numRows: Int       // Number of rows
    def numCols: Int       // Number of columns
    def matrix: Vector[Vector[T]]

    // Method to get a cell value at a specific position
    def cell(row: Int, col: Int): T = matrix(row)(col)

    // Method to fill the matrix with a specific value
    def fill(filling: T): MatrixInterface[T]

    // Method to replace a specific cell
    def replaceCell(row: Int, col: Int, cell: T): MatrixInterface[T]
}

case class Matrix[T](matrix : Vector[Vector[T]])extends MatrixInterface[T] {
    val numRows = matrix.size
    val numCols = matrix(0).size

    override def cell(row:Int, col:Int):T = matrix (row)(col)

    override def replaceCell(row:Int, col:Int, cell:T):Matrix[T] = copy(matrix.updated(row, matrix(row).updated(col, cell)))

    override def fill (filling:T):Matrix[T]= copy( Vector.tabulate(numRows, numCols){(row, col) => filling})

}

enum Stone {
  case Black, White, Empty

  override def toString: String = this match
    case Black => "S"    
    case White => "W"
    case Empty => "."

  def flip: Stone = this match
    case Black => White
    case White => Black
    case Empty => throw new UnsupportedOperationException("Empty cannot be flipped")
}
val zeroMatrix = Matrix(Vector.fill(3, 3)(Stone.Empty))




