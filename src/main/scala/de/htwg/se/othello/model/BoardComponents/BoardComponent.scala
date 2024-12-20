package de.htwg.se.othello.model.BoardComponents

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone

trait BoardComponent {
  def getBoard: MatrixInterface[StoneComponent]
  def numRows: Int
  def numCols: Int
  def placeStone(x: Int, y: Int, stone: StoneComponent): BoardComponent
  def copy(): BoardComponent
  override def toString: String
}
