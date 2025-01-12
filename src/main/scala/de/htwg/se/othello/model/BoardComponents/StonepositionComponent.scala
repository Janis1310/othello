package de.htwg.se.othello.model.BoardComponents

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import de.htwg.se.othello.model.BoardComponents.StoneComponent

trait StonepositionComponent {
  def x: Int
  def y: Int
  def stone: StoneComponent
}
