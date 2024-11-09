package de.htwg.se.othello.controller
import de.htwg.se.othello.model.{Board, Stone, Stoneposition, MoveHandler}
import de.htwg.se.othello.util.Observable

class Controller(var board:Board) extends Observable{
  def createNewBoard(size_x: Int, size_y: Int):Unit = {
    board = new Board(size_x, size_y)
    notifyObservers
  }


  def boardToString: String = board.toString

  def setNewStone(stonePosition: Stoneposition, board: Board):Unit = {
    val updatedBoard = MoveHandler.isValidMove(stonePosition, board) match {
      case true => MoveHandler.flipStones(stonePosition, board)
      case false => board
    }
    notifyObservers
  }

    def getBoard: Board = board




}