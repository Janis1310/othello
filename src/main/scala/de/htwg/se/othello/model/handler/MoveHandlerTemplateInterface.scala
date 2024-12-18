package de.htwg.se.othello.model.handler
import de.htwg.se.othello.model.{Board, Stoneposition}
import de.htwg.se.othello.model.Interface.BoardComponent

trait MoveHandlerTemplateInterface {
def processMove(stonePosition: Stoneposition, board: BoardComponent): BoardComponent
  def isValidMove(stonePosition: Stoneposition, board: BoardComponent): Boolean
  protected def flipStones(stonePosition: Stoneposition, board: BoardComponent): BoardComponent
  
}
