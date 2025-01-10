package de.htwg.se.othello.model.HandlerComponents

import de.htwg.se.othello.model.BoardComponents.{BoardComponent, StonepositionComponent}

trait MoveHandlerTemplateInterface {
  /**
   * Verarbeitet zug. Prüft zuerst ob zug möglich ist und flipt dann die Steine
   * @param stonePosition übergibt die Steinposition
   * @param board ist das aktuelle Spielfeld
   * @return
   */
  def processMove(stonePosition: StonepositionComponent, board: BoardComponent): BoardComponent
  def isValidMove(stonePosition: StonepositionComponent, board: BoardComponent): Boolean
  protected def flipStones(stonePosition: StonepositionComponent, board: BoardComponent): BoardComponent
  
}
