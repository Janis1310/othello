package de.htwg.se.othello.model

enum Stone {
  case Black, White, Empty

   override def toString: String = this match
    case Black => "S"    
    case White => "W"
    case Empty => "."

  def flip: Stone = this match
    case Black => White
    case White => Black
    case Empty => Empty

}

