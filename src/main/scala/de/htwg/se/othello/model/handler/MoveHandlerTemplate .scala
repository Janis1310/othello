package de.htwg.se.othello.model.handler

import de.htwg.se.othello.model.{Stoneposition, Board}

abstract class MoveHandlerTemplate {
  // Template-Methode: Die Hauptlogik, die von allen konkreten Klassen verwendet wird.
  final def processMove(stonePosition: Stoneposition, board: Board): Board = {
    if (!isValidMove(stonePosition, board)) {
      throw new IllegalArgumentException()
    }

    val updatedBoard = flipStones(stonePosition, board)
    updatedBoard
  }

  // Schritte, die von den Unterklassen implementiert werden müssen.
  protected def isValidMove(stonePosition: Stoneposition, board: Board): Boolean
  protected def flipStones(stonePosition: Stoneposition, board: Board): Board
}
