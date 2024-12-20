package de.htwg.se.othello.controller.Interface

import de.htwg.se.othello.model.Player
import de.htwg.se.othello.model.Interface.BoardComponent
import scala.collection.immutable.Queue
import de.htwg.se.othello.controller.GameState
import de.htwg.se.othello.util.Observable

trait ControllerComponent extends Observable{
    def boardToString: String
    def addPlayers(player1Name: String, player2Name: String): Unit
    def getPlayers: Queue[Player]
    def getCurrentPlayer: Player
    def nextPlayer(): Unit
    def makeMove(x: Int, y: Int): Either[String, String]
    def createNewBoard(rows: Int, cols: Int): BoardComponent
    def changeState(newState: GameState.GameState): Unit
    def getGameState: GameState.GameState
    def processTurn(curRow: Int, curCol: Int): Unit
    def undo: Unit
    def redo: Unit
    def setBoard(board: BoardComponent): Unit
  
}
