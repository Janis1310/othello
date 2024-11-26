package de.htwg.se.othello.model

// Product (interface)
trait Player {
  def name: String
  def stone: Stone
  override def toString: String = s"$name: $stone"
}

// concreteProduct: HumanPlayer
case class HumanPlayer(name: String, stone: Stone) extends Player

// concreteProduct: AIPlayer
case class AIPlayer(name: String, stone: Stone) extends Player

// (Creator)
abstract class PlayerCreator {
  def createPlayer(name: String, stone: Stone): Player //factoryMethod()
}
// concreteCreator: HumanPlayerCreator
class HumanPlayerCreator extends PlayerCreator {
  override def createPlayer(name: String, stone: Stone): Player = HumanPlayer(name, stone)//factoryMethod()
}

// concreteCreator: AIPlayerCreator
class AIPlayerCreator extends PlayerCreator {
  override def createPlayer(name: String, stone: Stone): Player = AIPlayer(name + " (AI)", stone) //factoryMethod()
}