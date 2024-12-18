package de.htwg.se.othello.model.handler

import de.htwg.se.othello.model.Interface.BoardComponent
import de.htwg.se.othello.model.Interface.StonepositionComponent

trait MoveHandlerTemplateInterface {
def processMove(stonePosition: StonepositionComponent, board: BoardComponent): BoardComponent
  def isValidMove(stonePosition: StonepositionComponent, board: BoardComponent): Boolean
  protected def flipStones(stonePosition: StonepositionComponent, board: BoardComponent): BoardComponent
  
}
