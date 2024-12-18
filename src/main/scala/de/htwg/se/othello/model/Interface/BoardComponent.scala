package de.htwg.se.othello.model.Interface

import de.htwg.se.othello.model.Matrix
import de.htwg.se.othello.model.Stone
import de.htwg.se.othello.model.MatrixInterface

trait BoardComponent {
  def getBoard: MatrixInterface[StoneComponent]
  def numRows: Int
  def numCols: Int
  def placeStone(x: Int, y: Int, stone: StoneComponent): BoardComponent
  def copy(): BoardComponent
  override def toString: String
}
