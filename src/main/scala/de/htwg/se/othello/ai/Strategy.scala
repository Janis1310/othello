package de.htwg.se.othello.ai
import scala.util.Random

import scala.util.control.Breaks._
import de.htwg.se.othello.model.{Board, Stone, Stoneposition, MoveHandler, Player}
import de.htwg.se.othello.util.Observable
import scala.collection.immutable.Queue


object StrategyContext {
    private var players: Queue[Player] = Queue()
    
    // Methode zum Setzen der Spieler
    def setPlayers(players: Queue[Player]): Unit = {
        this.players = players
    }

    def getPlayers: Queue[Player] = players
    def getCurrentPlayer: Player = players.head
    println(players)
    //if(players.nonEmpty) 
    var strategy = if (Random.nextInt() % 2 == 0) strategy1 else strategy2

        
    // Strategie 1: Iteriert vorwärts
    def strategy1(board: Board): Option[Stoneposition] = {
        println("Strategie 1:")
        val stone = getCurrentPlayer.stone
        var result: Option[Stoneposition] = None
        breakable {
        for (i <- 0 until board.numRows) {
            for (j <- 0 until board.numCols) {
            if (MoveHandler.isValidMove(Stoneposition(i, j, stone), board)) {
                result = Some(Stoneposition(i, j, stone))
                break() // man muss das so machen, weil return nicht abbrechen kann
            }
            }
        }
        }
        result // Rückgabe des Ergebnisses
    }

    // Strategie 2: Iteriert rückwärts
    def strategy2(board: Board): Option[Stoneposition] = {
        println("Strategie 2:")
        val stone = getCurrentPlayer.stone
        var result: Option[Stoneposition] = None
        breakable {
        for (i <- (0 until board.numRows).reverse) {
            for (j <- (0 until board.numCols).reverse) {
            if (MoveHandler.isValidMove(Stoneposition(i, j, stone), board)) {
                result = Some(Stoneposition(i, j, stone))
                break() 
            }
            }
        }
        }
        result // Rückgabe des Ergebnisses
    }
    
}
// StrategyContext.strategy