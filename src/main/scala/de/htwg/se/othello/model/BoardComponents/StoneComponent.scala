package de.htwg.se.othello.model.BoardComponents

import play.api.libs.json._
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone

trait StoneComponent {
  def flip: StoneComponent
  def toString: String
}

object StoneComponent{
  implicit val stoneWrites: Writes[StoneComponent] = Writes { 
    case Stone.Empty => JsString("Empty") 
    case Stone.White => JsString("White") 
    case Stone.Black => JsString("Black")
  }
}
