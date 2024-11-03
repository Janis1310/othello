package de.htwg.se.othello.model

object moveValidator {
    def isValidMove(stoneposition: Stoneposition, board: Board): Boolean = {
        if ( board.getBoard.cell(stoneposition.x, stoneposition.y)!= Stone.Empty) { //field is empty?
            println("Field is not empty")
            return false
        }

        if(stoneposition.x < 0 || stoneposition.x >= board.getBoard.numRows || stoneposition.y < 0 || stoneposition.y >= board.getBoard.numCols) {
          printf("Ungültige Position: (" + "%d, %d)", stoneposition.x, stoneposition.y)
          return false
        }

        val opponent = if (stoneposition.stone == Stone.White) Stone.Black else Stone.White
        val directions = Seq(
          (0, 1),   // rechts
          (1, 1),   // rechts unten
          (1, 0),   // unten
          (1, -1),  // links unten
          (0, -1),  // links
          (-1, -1), // links oben
          (-1, 0),  // oben
          (-1, 1)   // rechts oben
        )
        directions.exists { case (dx, dy) =>
      checkDirection(dx, dy, stoneposition.x + dx, stoneposition.y + dy, false, opponent)
    }
        

      return false
    }

     def checkDirection(dx: Int, dy: Int, x: Int, y: Int, foundOpponent: Boolean, opponent: Stone): Boolean = {
    if (x < 0 || x >= board.numRows || y < 0 || y >= board.numCols) {
      return false // Aus dem Board heraus
    }

    board.cell(x, y) match {
      case `opponent` => checkDirection(dx, dy, x + dx, y + dy, true, opponent) // Gegnerstein gefunden
      case `player` if foundOpponent => true // Gültiger Zug gefunden
      case Stone.Empty => false // Leeres Feld, keine gültige Reihe
      case _ => false // Eigenes Feld ohne Gegnersteine
    }
  }

  }

        
   
