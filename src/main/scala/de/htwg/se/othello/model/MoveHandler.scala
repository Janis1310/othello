package de.htwg.se.othello.model

object MoveHandler {

  private val directions = Seq(
          (0, 1),   // rechts
          (1, 1),   // rechts unten
          (1, 0),   // unten
          (1, -1),  // links unten
          (0, -1),  // links
          (-1, -1), // links oben
          (-1, 0),  // oben
          (-1, 1)   // rechts oben
        )
    def isValidMove(stoneposition: Stoneposition, board: Board): Boolean = {
        if ( board.getBoard.cell(stoneposition.x, stoneposition.y)!= Stone.Empty) { //field is empty?
            println("Field is not empty")
            return false
        }

        if(stoneposition.x < 0 || stoneposition.x >= board.getBoard.numRows || stoneposition.y < 0 || stoneposition.y >= board.getBoard.numCols) {
          printf("Ungültige Position: (" + "%d, %d)", stoneposition.x, stoneposition.y)
          return false
        }

        val player = stoneposition.stone
        val opponent = if (stoneposition.stone == Stone.White) Stone.Black else Stone.White
        
        directions.exists { case (dx, dy) =>
        checkDirection(dx, dy, stoneposition.x + dx, stoneposition.y + dy, false, opponent, player, board)
        }
      }

    private def checkDirection(dx: Int, dy: Int, x: Int, y: Int, foundOpponent: Boolean, opponent: Stone, player: Stone, board: Board): Boolean = {
    if (x < 0 || x >= board.getBoard.numRows || y < 0 || y >= board.getBoard.numCols) {
      return false // Aus dem Board heraus
    }

    board.getBoard.cell(x, y) match {
      case `opponent` => 
        checkDirection(dx, dy, x + dx, y + dy, true, opponent, player, board) // Gegnerstein gefunden, weiter in die Richtung
      case `player` if foundOpponent => 
        true // Gültiger Zug gefunden
      case Stone.Empty => false // Leeres Feld, keine gültige Reihe
      case _ => false // Eigenes Feld ohne Gegnersteine
    }
  }

  
  
  def flipStones(stonePosition: Stoneposition, board: Board): String = {
  val player = stonePosition.stone
  val opponent = if (player == Stone.White) Stone.Black else Stone.White

  // Überprüfe, ob der Zug gültig ist
  if (!isValidMove(stonePosition, board)) {
    return board.toString // Gib das Board als String zurück, wenn der Zug ungültig ist
  }

  // Setze den Stein auf das Board, wenn der Zug gültig ist
  var updatedBoard = board.placeStone(stonePosition.x, stonePosition.y, player)

  // Iteriere über alle Richtungen und flippe die Steine, wenn möglich
  directions.foreach { case (dx, dy) =>
    updatedBoard = flipDirection(dx, dy, stonePosition.x + dx, stonePosition.y + dy, opponent, player, updatedBoard)
  }

  // Gib das aktualisierte Board als String zurück
  updatedBoard.toString
}



  private def flipDirection(dx: Int, dy: Int, x: Int, y: Int, opponent: Stone, player: Stone, board: Board): Board = {


    board.getBoard.cell(x, y) match {
      case `opponent` =>
        val newBoard = board.placeStone(x, y, player)
        flipDirection(dx, dy, x + dx, y + dy, opponent, player, newBoard)

      case `player` =>
        // Wenn ein eigener Stein gefunden wurde und Gegnersteine geflippt wurden
        return board  // Rekursion stoppen und das Board zurückgeben

      case _ => 
        // Wenn die Zelle leer ist oder ein anderes Feld gefunden wird, stoppe die Rekursion
        return board  // Rekursion stoppen und das Board zurückgeben
    }
  }

}

