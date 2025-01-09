package de.htwg.se.othello.model.HandlerComponents

import de.htwg.se.othello.model.BoardComponents.{
  BoardComponent,
  StonepositionComponent
}

trait MoveHandlerTemplateInterface {
  def processMove(
      stonePosition: StonepositionComponent,
      board: BoardComponent
  ): BoardComponent
  def isValidMove(
      stonePosition: StonepositionComponent,
      board: BoardComponent
  ): Boolean
  protected def flipStones(
      stonePosition: StonepositionComponent,
      board: BoardComponent
  ): BoardComponent

}
