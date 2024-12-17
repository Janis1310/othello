package de.htwg.se.othello.model.handler
import de.htwg.se.othello.model.{Board, Stoneposition}

trait MoveHandlerTemplateInterface {
def processMove(stonePosition: Stoneposition, board: Board): Board
  def isValidMove(stonePosition: Stoneposition, board: Board): Boolean
  protected def flipStones(stonePosition: Stoneposition, board: Board): Board
  
}
