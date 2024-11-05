package de.htwg.se.othello.model

object moveValidator {

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
        val valid_pos = Seq.empty[(Int, Int)]
        
        directions.exists { case (dx, dy) =>
        checkDirection(dx, dy, stoneposition.x + dx, stoneposition.y + dy, false, opponent, player, board, valid_pos)
        }
      }

      private def checkDirection(dx: Int, dy: Int, x: Int, y: Int, foundOpponent: Boolean, opponent: Stone, player: Stone, board: Board, valid_pos: Seq[(Int, Int)]): Boolean = {
    // Überprüfen, ob die Position innerhalb der Grenzen ist
    if (x < 0 || x >= board.getBoard.numRows || y < 0 || y >= board.getBoard.numCols) {
      return false // Aus dem Board heraus
    }

    board.getBoard.cell(x, y) match {
      case `opponent` => 
        checkDirection(dx, dy, x + dx, y + dy, true, opponent, player, board, valid_pos) // Gegnerstein gefunden, weiter in die Richtung
      case `player` if foundOpponent => true // Gültiger Zug gefunden
      case Stone.Empty => false // Leeres Feld, keine gültige Reihe
      case _ => false // Eigenes Feld ohne Gegnersteine
    }
  }
        

}

