package de.htwg.se.othello.model.Interface

import de.htwg.se.othello.model.Matrix
import de.htwg.se.othello.model.Stone

trait BoardComponent {
  def getBoard: Matrix[Stone]
  def numRows: Int
  def numCols: Int
  def placeStone(x: Int, y: Int, stone: Stone): BoardComponent
  def copy(): BoardComponent
  override def toString: String
}
