package de.htwg.se.othello.model

enum Stone {
  case Black, White, Empty

   override def toString: String = this match
    case Black => "S"    
    case White => "W"
    case Empty => "."
}
