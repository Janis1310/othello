package de.htwg.se.othello.model

sealed trait PlayerType
case object Human extends PlayerType
case object AI extends PlayerType

object PlayerFactory {
  def createPlayer(name: String, stone: Stone, playerType: PlayerType): Player = playerType match {
    case Human => Player(name, stone)
    case AI    => Player(name + " (AI)", stone)
  }
}

case class Player(name:String, stone: Stone) {
    override def toString(): String = name + ": " + stone
  
}
