package de.htwg.se.othello.model.BoardComponents

trait StoneComponent {
  def flip: StoneComponent
  def toString: String
}
