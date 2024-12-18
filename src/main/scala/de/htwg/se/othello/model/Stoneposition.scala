package de.htwg.se.othello.model

import de.htwg.se.othello.model.Interface.StonepositionComponent
import de.htwg.se.othello.model.Interface.StoneComponent

case class Stoneposition(x: Int, y: Int, stone: StoneComponent) extends StonepositionComponent