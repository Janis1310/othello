package de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{
  Board,
  Stoneposition
}
import de.htwg.se.othello.model.BoardComponents.{
  BoardComponent,
  StonepositionComponent
}
import de.htwg.se.othello.model.HandlerComponents.MoveHandlerTemplateInterface

abstract class MoveHandlerTemplate extends MoveHandlerTemplateInterface {
  
  // für was brauchen wir das?
  final def processMove(stonePosition: StonepositionComponent, board: BoardComponent): BoardComponent = {
    if (!isValidMove(stonePosition, board)) {
      throw new IllegalArgumentException()
    }

    val updatedBoard = flipStones(stonePosition, board)
    updatedBoard
  }

  def isValidMove(stonePosition: StonepositionComponent, board: BoardComponent): Boolean
  protected def flipStones(stonePosition: StonepositionComponent, board: BoardComponent): BoardComponent
}
