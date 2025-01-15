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

  implicit val stoneReads: Reads[StoneComponent] = Reads {
    case JsString("Empty") => JsSuccess(Stone.Empty)
    case JsString("White") => JsSuccess(Stone.White)
    case JsString("Black") => JsSuccess(Stone.Black)
    case _ => JsError("Unknown stone type")
  }
}
