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

    /**
     * Führt einen Zug aus.
     * Prüft die Gültigkeit des Zuges und aktualisiert das Spielfeld und den Zustand entsprechend.
     *
     * @param x Zeilenposition des Zuges
     * @param y Spaltenposition des Zuges
     * @return True, wenn der Zug erfolgreich war, andernfalls False
     */
    def makeMove(x: Int, y: Int): Boolean
    def createNewBoard(rows: Int, cols: Int): BoardComponent
    def changeState(newState: GameState.GameState): Unit
    def getGameState: GameState.GameState

    /**
     * Verarbeitet den Zug eines Spielers (Mensch oder KI).
     * Entscheidet, ob der nächste Zug durch den Spieler oder durch die KI erfolgt. Ruft dann MakeMove(x, y) auf.
     *
     * @param curRow Zeilenposition des Zuges
     * @param curCol Spaltenposition des Zuges
     * @return True, wenn der Zug erfolgreich war, andernfalls False
     */
    def processTurn(curRow: Int, curCol: Int): Boolean
    
    def undo: Unit
    def redo: Unit
    def setBoard(board: BoardComponent): Unit
    def getBoard: BoardComponent

    def setGameMode(mode: String): Unit
    def getGameMode: String

    def processAITurn() : Unit
  
}
