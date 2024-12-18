package de.htwg.se.othello.model
import de.htwg.se.othello.model.handler.MoveHandlerTemplate
import de.htwg.se.othello.model.Interface.BoardComponent
import de.htwg.se.othello.model.Interface.StoneComponent

object MoveHandler extends MoveHandlerTemplate{

  private val directions = Seq(
          (0, 1),
          (1, 1),
          (1, 0),
          (1, -1),
          (0, -1),
          (-1, -1),
          (-1, 0),
          (-1, 1)
        )
    override def isValidMove(stoneposition: Stoneposition, board: BoardComponent): Boolean = {

        if(stoneposition.x < 0 || stoneposition.x >= board.getBoard.numRows || stoneposition.y < 0 || stoneposition.y >= board.getBoard.numCols) {
          printf("Ungültige Position: (" + "%d, %d)", stoneposition.x, stoneposition.y)
          return false
        }
        
        if ( board.getBoard.cell(stoneposition.x, stoneposition.y)!= Stone.Empty) { 
            println("Field is not empty")
            return false
        }

        val player = stoneposition.stone
        val opponent = stoneposition.stone.flip
        
        directions.exists { case (dx, dy) =>
        checkDirection(dx, dy, stoneposition.x + dx, stoneposition.y + dy, false, opponent, player, board)
        }
      }
    // checkDirction(x_newdirection, y_newdirection, x_positionCurrent, y_positionCurrent, ?foundOpponent?, opponentColor, playerColor, board)
    private def checkDirection(dx: Int, dy: Int, x: Int, y: Int, foundOpponent: Boolean, opponent: StoneComponent, player: StoneComponent, board: BoardComponent): Boolean = {
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

  override protected def flipStones(stonePosition: Stoneposition, board: BoardComponent): BoardComponent = {
    val player = stonePosition.stone
    val opponent = player.flip

    var updatedBoard = board.placeStone(stonePosition.x, stonePosition.y, player)
    
    directions.foreach { case (dx, dy) =>
      updatedBoard = flipDirection(dx, dy, stonePosition.x + dx, stonePosition.y + dy, opponent, player, updatedBoard)
    }
    updatedBoard
  }



  private def flipDirection(dx: Int, dy: Int, x: Int, y: Int, opponent: StoneComponent, player: StoneComponent, board: BoardComponent): BoardComponent = {

    if (x < 0 || x >= board.getBoard.numRows || y < 0 || y >= board.getBoard.numCols) 
      return board // direction is out of bounds

    board.getBoard.cell(x, y) match {
      case `opponent` =>
        // Geh in die Richtung weiter, um das Ende der Reihe zu suchen
        val flipboard = flipDirection(dx, dy, x + dx, y + dy, opponent, player, board)
        
        // Falls ein `player`-Stein das Ende markiert hat, flippe den aktuellen `opponent`-Stein
        if (flipboard.getBoard.cell(x + dx, y + dy) == player) {
          flipboard.placeStone(x, y, player)
        } else {
          return board // Keine Umwandlung, da nicht von `player` eingeschlossen
        }

      case `player` =>
        // Ende der Kette gefunden, Rückgabe für das Flippen in anderen rekursiven Aufrufen
        return board

      case _ => 
        // Wenn die Zelle leer ist oder ein anderes Feld gefunden wird, beenden ohne zu flippen
        return board
    }
  }
}