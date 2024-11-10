package de.htwg.se.othello.controller
import de.htwg.se.othello.model.{Board, Stone, Stoneposition, MoveHandler, Player}
import de.htwg.se.othello.util.Observable

// Handles requests from the view and updates the model
class Controller(var board:Board) extends Observable{
/*  def createNewBoard(size_x: Int, size_y: Int):Unit = {
    board = new Board(size_x, size_y)
    notifyObservers
  }
*/

  def boardToString: String = board.toString

  def setNewStoneController(stonePosition: Stoneposition, board: Board):Board = {
    val updatedBoard = MoveHandler.isValidMove(stonePosition, board) match {
      case true => MoveHandler.flipStones(stonePosition, board)
      case false => board
    }
    notifyObservers
    updatedBoard
  }
  def isValidMoveController(stonePosition: Stoneposition, board: Board): Boolean = MoveHandler.isValidMove(stonePosition, board)

  // def getBoard: Board = board

  def newStonePositionController(x: Int, y: Int, stone: Stone): Stoneposition = Stoneposition(x, y, stone)

  def newPlayerController(name: String, stone: Stone): Player = {
    val player = Player(name, stone)
    notifyObservers
    player
  }
}