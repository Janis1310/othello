package de.htwg.se.othello.model.FileIOComponent.FileIoXML

import de.htwg.se.othello.model.FileIOComponent.FileIOInterface
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.Playercomponents.Player
import scala.xml.{NodeSeq, PrettyPrinter, Elem, Node}
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import de.htwg.se.othello.model.Playercomponents.{_}
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board

class FileIO extends FileIOInterface {

  override def save(board: BoardComponent, curretplayer: Player, nextplayer: Player, mode: String): Unit = {

    val xml = <gamestate>
      {playerToXML("currentplayer", curretplayer)}{playerToXML("nextplayer", nextplayer)}<mode>mode</mode>{boardToXML(board)}
    </gamestate>

    import java.io._
    val pw = new PrintWriter(new File("gamestate.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xmlFormat = prettyPrinter.format(xml)
    pw.write(xmlFormat)
    pw.close()
  }

  override def load(): (BoardComponent, Player, Player, String) = {
    val xml = scala.xml.XML.loadFile("gamestate.xml")
    val currentplayer = playerFromXML((xml \\ "player").filter(p => (p \ "currentplayer").text == "currentplayer").head)
    val nextplayer = playerFromXML((xml \\ "player").filter(p => (p \ "currentplayer").text == "nextplayer").head)
    val mode = (xml \\ "mode").text
    val boardNode = (xml \ "board").head
    val board = boardFromXML(boardNode)

    (board, currentplayer, nextplayer, mode)

  }

  def playerToXML(playertype: String, player: Player): Elem = {
    <player>
      <role>
        {player.role}
      </role>
      <name>
        {player.name}
      </name>
      <stone>
        {player.stone.toString}
      </stone>
      <currentplayer>
        {playertype}
      </currentplayer>
    </player>
  }


  def boardToXML(board: BoardComponent): Elem = {
    val numrows = board.getBoard.numRows
    val numcols = board.getBoard.numCols
    val matrixXml = for {
      row <- 0 until board.getBoard.numRows
      col <- 0 until board.getBoard.numCols
    } yield {
      <cell>
        <row>
          {row}
        </row>
        <col>
          {col}
        </col>
        <color>
          {board.getStoneAt(row, col).toString}
        </color>
      </cell>
    }

    <board>
      <numrows>
        {numrows}
      </numrows>
      <numcols>
        {numcols}
      </numcols>
      <matrix>
        {matrixXml}
      </matrix>
    </board>

  }

  def playerFromXML(node: Node): Player = {
    val role = (node \ "role").text
    val name = (node \ "name").text
    val stoneString = (node \ "stone").text

    val stone = stoneString match {
      case "W" => Stone.White
      case "S" => Stone.Black
      case "." => Stone.Empty
      case _ => throw new Exception(s"Unrecognized stone: $stoneString")
    }

    role match {
      case "Human" => HumanPlayer(name, stone)
      case "AI" => AIPlayer(name, stone)
      case _ => throw new Exception(s"Unrecognized role: $role")
    }
  }

  def boardFromXML(xml: Node): BoardComponent = {
    val numrow = (xml \ "numrows").text.toInt
    val numcol = (xml \ "numcols").text.toInt
    var board = Board(numrow, numcol)

    val matrix = (xml \ "matrix" \ "cell")

    for (cell <- matrix) {
      val row = (cell \ "row").text.toInt
      val col = (cell \ "col").text.toInt
      val stoneString = (cell \ "color").text
      val stone = stoneString match {
        case "W" => Stone.White
        case "S" => Stone.Black
        case "." => Stone.Empty
        case _ => throw new Exception(s"Unrecognized stone: $stoneString")
      }

      board = Board(board.getBoard.replaceCell(row, col, stone))
    }
    board

  }


}