package de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone.Stone
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Stone, Stoneposition}
import de.htwg.se.othello.model.BoardComponents.{BoardComponent, StoneComponent, StonepositionComponent}
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandlerTemplate
import de.htwg.se.othello.model.Playercomponents.Player.getCurrentPlayer

import scala.util.control.Breaks.{break, breakable}

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


  def isAnyMovePossible(board: BoardComponent, currentPlayerStone: Stone): Boolean = {
    (0 until board.numRows).exists(i =>
      (0 until board.numCols).exists(j =>
        isValidMove(Stoneposition(i, j, currentPlayerStone), board)
      )
    )
  }

  override def isValidMove(stoneposition: StonepositionComponent, board: BoardComponent): Boolean = {

        if(stoneposition.x < 0 || stoneposition.x >= board.getBoard.numRows || stoneposition.y < 0 || stoneposition.y >= board.getBoard.numCols) {
          printf("Ungültige Position: (" + "%d, %d)", stoneposition.x, stoneposition.y) // Außerhalb vom brett
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
  /**
   * Überprüft in einer bestimmten Richtung, ob ein gültiger Zug möglich ist, d.h. ob der Spieler Steine umdrehen kann.
   *
   * @param dx Die Veränderung der x-Position (senkrecht).
   * @param dy Die Veränderung der y-Position (waagerecht).
   * @param x Die aktuelle x-Position des Feldes, das überprüft wird.
   * @param y Die aktuelle y-Position des Feldes, das überprüft wird.
   * @param foundOpponent Ein Flag, das angibt, ob bereits ein Gegnerstein gefunden wurde.
   * @param opponent Der Gegnerstein, der umgedreht werden soll.
   * @param player Der Spielerstein, der den Zug macht.
   * @param board Das Spielfeld, auf dem das Spiel stattfindet.
   * @return True, wenn der Zug gültig ist, andernfalls false.
   */
    private def checkDirection(dx: Int, dy: Int, x: Int, y: Int, foundOpponent: Boolean, opponent: StoneComponent, player: StoneComponent, board: BoardComponent): Boolean = {
      if (x < 0 || x >= board.getBoard.numRows || y < 0 || y >= board.getBoard.numCols) {
        return false // Aus dem Board heraus
      }
      board.getBoard.cell(x, y) match {
        case `opponent` =>
          checkDirection(dx, dy, x + dx, y + dy, true, opponent, player, board) // Gegnerstein gefunden, weiter in die Richtung
        case `player` if foundOpponent =>
          true
        case Stone.Empty => false // Leeres Feld, keine gültige Reihe
        case _ => false // Eigenes Feld ohne Gegnersteine
      }
  }

  override protected def flipStones(stonePosition: StonepositionComponent, board: BoardComponent): BoardComponent = {
    val player = stonePosition.stone
    val opponent = player.flip

    var updatedBoard = board.placeStone(stonePosition.x, stonePosition.y, player)
    
    directions.foreach { case (dx, dy) =>
      updatedBoard = flipDirection(dx, dy, stonePosition.x + dx, stonePosition.y + dy, opponent, player, updatedBoard)
    }
    updatedBoard
  }


  /**
   * Dreht die Steine in einer bestimmten Richtung um, falls erforderlich.
   *
   * @param dx Die Veränderung der x-Position (senkrecht).
   * @param dy Die Veränderung der y-Position (waagerecht).
   * @param x Die aktuelle x-Position des Feldes, das überprüft wird.
   * @param y Die aktuelle y-Position des Feldes, das überprüft wird.
   * @param opponent Der Gegnerstein, der umgedreht werden soll.
   * @param player Der Spielerstein, der den Zug macht.
   * @param board Das Spielfeld, auf dem das Spiel stattfindet.
   * @return Ein aktualisiertes Spielfeld, bei dem gegebenenfalls Steine umgedreht wurden.
   */
  private def flipDirection(dx: Int, dy: Int, x: Int, y: Int, opponent: StoneComponent, player: StoneComponent, board: BoardComponent): BoardComponent = {

    if (x < 0 || x >= board.getBoard.numRows || y < 0 || y >= board.getBoard.numCols) 
      //println("------------------test1-----------------")
      return board // direction is out of bounds

    board.getBoard.cell(x, y) match {
      case `opponent` =>
        if (x + dx < 0 || x + dx >= board.getBoard.numRows || y + dy < 0 || y + dy >= board.getBoard.numCols) {
          //println("------------------test2-----------------")
          return board // Keine gültige Richtung, Rückgabe ohne Änderung
        }
        // Geh in die Richtung weiter, um das Ende der Reihe zu suchen
        val flipboard = flipDirection(dx, dy, x + dx, y + dy, opponent, player, board)
        
        // Falls ein `player`-Stein das Ende markiert hat, flippe den aktuellen `opponent`-Stein
        if (flipboard.getBoard.cell(x + dx, y + dy) == player) {
          flipboard.placeStone(x, y, player)
        } else {
          //println("------------------test3-----------------")
          board // Keine Umwandlung, da nicht von `player` eingeschlossen
        }

      case `player` =>
        board

      case _ =>
        board
    }
  }
}