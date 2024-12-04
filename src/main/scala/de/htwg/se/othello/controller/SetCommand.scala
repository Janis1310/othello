package de.htwg.se.othello.controller

import de.htwg.se.othello.model.Stone
import de.htwg.se.othello.model.MoveHandler
import de.htwg.se.othello.util.Command

class SetCommand(row:Int, col: Int, stone:Stone, controller: Controller) extends Command {
  override def doStep: Unit = {
    Controller.processTurn()
    controller.board = controller.board.placeStone(row, col, stone)
  }

  override def undoStep: Unit = controller.board = controller.board.placeStone(row, col, Stone.Empty) // Das muss noch angepasst werden

  override def redoStep: Unit = controller.board = controller.board.placeStone(row, col, stone)
}
