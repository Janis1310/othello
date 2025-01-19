package de.htwg.se.othello.controller.ControllerComponents

import de.htwg.se.othello.model.BoardComponents.BoardComponent
import scala.collection.immutable.Queue
import de.htwg.se.othello.model.Playercomponents.Player
import de.htwg.se.othello.util.Observable

trait ControllerComponent extends Observable{
    def boardToString: String
    def addPlayers(playerName: String): Unit
    def getPlayers: Queue[Player]
    def getCurrentPlayer: Player
    def nextPlayer(): Unit
    def makeMove(x: Int, y: Int): Boolean
    def createNewBoard(rows: Int, cols: Int): BoardComponent
    def changeState(newState: GameState.GameState): Unit
    def getGameState: GameState.GameState
    
    def undo: Unit
    def redo: Unit
    def setBoard(board: BoardComponent): Unit
    def getBoard: BoardComponent

    def setGameMode(mode: String): Unit
    def getGameMode: String

    def processAITurn() : Boolean

    def countStone() : (Int, Int)

    def load() : Unit

    def save() : Unit

    def getWinner() : Option[String]
  
}
