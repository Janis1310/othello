package de.htwg.se.othello.model.BoardComponents.BoardBaseImpl

import de.htwg.se.othello.model.BoardComponents.{StoneComponent, StonepositionComponent}

case class Stoneposition(x: Int, y: Int, stone: StoneComponent) extends StonepositionComponent