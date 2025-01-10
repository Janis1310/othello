package de.htwg.se.othello.controller.ControllerComponents

import de.htwg.se.othello.model.BoardComponents.BoardComponent
import scala.collection.immutable.Queue
import de.htwg.se.othello.model.Playercomponents.Player
import de.htwg.se.othello.util.Observable

trait ControllerComponent extends Observable{
    def boardToString: String
    def addPlayers(player1Name: String, player2Name: String): Unit
    def getPlayers: Queue[Player]
    def getCurrentPlayer: Player
    def nextPlayer(): Unit
    def makeMove(x: Int, y: Int): Boolean
    def createNewBoard(rows: Int, cols: Int): BoardComponent
    def changeState(newState: GameState.GameState): Unit
    def getGameState: GameState.GameState
    def processTurn(curRow: Int, curCol: Int): Boolean
    def undo: Unit
    def redo: Unit
    def setBoard(board: BoardComponent): Unit
  
}
