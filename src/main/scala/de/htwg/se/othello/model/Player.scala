package de.htwg.se.othello.model

case class Player(name:String, stone: Stone) {
    override def toString(): String = name + ": " + stone
  
}
