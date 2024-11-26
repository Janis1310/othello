package de.htwg.se.othello.model

// Product (interface)
trait Player {
  def name: String
  def stone: Stone
  override def toString: String = s"$name: $stone"
}

// concreteProduct: HumanPlayer
private class HumanPlayer(val name: String, val stone: Stone) extends Player {
  override def toString: String = s"$name: $stone"
}

// concreteProduct: AIPlayer
private class AIPlayer(val name: String, val stone: Stone) extends Player {
  override def toString: String = s"$name (AI): $stone"
}

object Player {
  def apply(name: String, stone: Stone, kind: String) = kind match {
    case "Human" => HumanPlayer(name, stone)
    case "AI" => AIPlayer(name, stone)
  }
}