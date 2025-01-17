package de.htwg.se.othello.ai

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone, Stoneposition}
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandler
import de.htwg.se.othello.model.Playercomponents.Player
import de.htwg.se.othello.model.Playercomponents.Player.getCurrentPlayer

import scala.util.Random
import scala.util.control.Breaks.*
import de.htwg.se.othello.util.Observable

import scala.collection.immutable.Queue

object StrategyContext {

  def strategy: BoardComponent => Option[Stoneposition] = {
    if (Random.nextInt() % 2 == 0) strategy1 else strategy2
  }
  // Strategie 1: Iteriert vorwärts
  def strategy1(board: BoardComponent): Option[Stoneposition] = {
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
  def strategy2(board: BoardComponent): Option[Stoneposition] = {
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
