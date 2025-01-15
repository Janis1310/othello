package de.htwg.se.othello.model.FileIOComponent.FileIoJSON

import de.htwg.se.othello.model.FileIOComponent.FileIOInterface
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.BoardComponents.StoneComponent
import play.api.libs.json._
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import de.htwg.se.othello.model.Playercomponents.Player
import de.htwg.se.othello.model.Playercomponents.HumanPlayer
import de.htwg.se.othello.model.Playercomponents.AIPlayer
import scala.collection.immutable.Queue

class FileIO extends FileIOInterface{

  override def save(board: BoardComponent, curretplayer: Player, nextplayer: Player, mode: String): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gamestate.json"))
    pw.write(Json.prettyPrint(gameStateToJson(board, curretplayer, nextplayer, mode)))
    pw.close

  }

  override def load(): BoardComponent = ???

  // implicit val stoneWrites: Writes[StoneComponent] = Writes {
  //   case Stone.Empty => JsString("Empty")
  //   case Stone.White => JsString("White")
  //   case Stone.Black => JsString("Black")
  // }

//   implicit val stoneReads: Reads[StoneComponent] = Reads {
//     case JsString("Empty") => JsSuccess(Stone.Empty)
//     case JsString("White") => JsSuccess(Stone.White)
//     case JsString("Black") => JsSuccess(Stone.Black)
//     case _ => JsError("Unknown stone type")
//   }

//    implicit val playerWrites: Writes[Player] = new Writes[Player] {
//   override def writes(player: Player): JsValue = player match {
//     case human: HumanPlayer =>
//       Json.obj(
//         "name" -> human.name,
//         "stone" -> Json.toJson(human.stone),
//         "role" -> human.role
//       )
//     case ai: AIPlayer =>
//       Json.obj(
//         "name" -> ai.name,
//         "stone" -> Json.toJson(ai.stone),
//         "role" -> ai.role
//       )
//   }
// }
  def gameStateToJson(board: BoardComponent, curretplayer: Player, nextplayer: Player, mode: String)= {
    Json.obj(
      "currentplayer" -> Json.toJson(curretplayer),
      "nextplayer" -> Json.toJson(nextplayer),
      "mode" -> mode,
      "numrows" -> board.getBoard.numRows,
      "numcols" -> board.getBoard.numCols,
      "matrix" -> Json.toJson(
        for {
          row <- 0 until board.getBoard.numRows;
          col <- 0 until board.getBoard.numCols
        } yield{
          Json.obj(
            "row" -> row,
            "col" -> col,
            "color" -> Json.toJson(board.getBoard.cell(row, col))
          )
        }
      )


    )

  }


}
