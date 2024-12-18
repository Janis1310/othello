package de.htwg.se.othello.model.handler

import de.htwg.se.othello.model.{Stoneposition, Board}
import de.htwg.se.othello.model.Interface.BoardComponent

abstract class MoveHandlerTemplate extends MoveHandlerTemplateInterface{

  final def processMove(stonePosition: Stoneposition, board: BoardComponent): BoardComponent = {
    if (!isValidMove(stonePosition, board)) {
      throw new IllegalArgumentException()
    }

    val updatedBoard = flipStones(stonePosition, board)
    updatedBoard
  }

  def isValidMove(stonePosition: Stoneposition, board: BoardComponent): Boolean
  protected def flipStones(stonePosition: Stoneposition, board: BoardComponent): BoardComponent
}
