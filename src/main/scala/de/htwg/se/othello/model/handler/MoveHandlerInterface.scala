package de.htwg.se.othello.model.handler

import de.htwg.se.othello.model.{Stoneposition, Board}

trait MoveHandlerInterface {
  def isValidMove(stonePosition: Stoneposition, board: Board): Boolean
  def flipStones(stonePosition: Stoneposition, board: Board): Board
}
