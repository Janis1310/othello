package de.htwg.se.othello.model.handler

import de.htwg.se.othello.model.{Stoneposition, Board}
import de.htwg.se.othello.model.Interface.BoardComponent
import de.htwg.se.othello.model.Interface.StonepositionComponent

abstract class MoveHandlerTemplate extends MoveHandlerTemplateInterface{

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
