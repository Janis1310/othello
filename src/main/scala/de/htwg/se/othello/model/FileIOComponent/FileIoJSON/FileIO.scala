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
import scala.io.Source
import com.google.inject.Guice
import de.htwg.se.othello.OthelloModule
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board

class FileIO extends FileIOInterface{

  override def save(board: BoardComponent, curretplayer: Player, nextplayer: Player, mode: String): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gamestate.json"))
    pw.write(Json.prettyPrint(gameStateToJson(board, curretplayer, nextplayer, mode)))
    pw.close

  }

  override def load(): (BoardComponent, Player, Player, String) = {

    val source: String = Source.fromFile("gameState.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val injector = Guice.createInjector(new OthelloModule)

    val currentplayer = (json \ "currentplayer").validate[Player] match {
    case JsSuccess(player, _) => player // Erfolgreiche Deserialisierung
    case JsError(errors) =>
      println(s"Fehler bei der Deserialisierung von currentplayer: $errors")
      throw new RuntimeException("Fehler bei der Deserialisierung von currentplayer") // Fehler werfen, falls deserialization scheitert
  }

  // Deserialisierung des nextplayer
    val nextplayer = (json \ "nextplayer").validate[Player] match {
      case JsSuccess(player, _) => player // Erfolgreiche Deserialisierung
      case JsError(errors) =>
        println(s"Fehler bei der Deserialisierung von nextplayer: $errors")
        throw new RuntimeException("Fehler bei der Deserialisierung von nextplayer") // Fehler werfen, falls deserialization scheitert
    }
    
    val mode = (json \ "mode").as[String]

    val board = loadBoardFromJson(json)

    (board, currentplayer, nextplayer, mode)
  }

  def loadBoardFromJson(json: JsValue): BoardComponent ={
    val numRows = (json \ "numrows").as[Int]
    val numCols = (json \ "numcols").as[Int]

    val matrixData = (json \ "matrix").as[List[JsObject]]

    var board = Board(numRows, numCols)

    matrixData.foreach { stoneData =>
      val row = (stoneData \ "row").as[Int]
      val col = (stoneData \ "col").as[Int]
      val stone = (stoneData \ "color").as[StoneComponent]
      board = Board(board.getBoard.replaceCell(row, col, stone))
    }
    board
  }
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
