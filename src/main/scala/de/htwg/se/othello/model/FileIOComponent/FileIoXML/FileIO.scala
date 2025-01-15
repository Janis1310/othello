package de.htwg.se.othello.model.FileIOComponent.FileIoXML

import de.htwg.se.othello.model.FileIOComponent.FileIOInterface
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.Playercomponents.Player
import scala.xml.{ NodeSeq, PrettyPrinter, Elem}

class FileIO extends FileIOInterface{

  override def save(board: BoardComponent, curretplayer: Player, nextplayer: Player, mode: String): Unit = {

    val xml = <gamestate>
    {playerToXML("currentplayer",curretplayer)}
    {playerToXML("nextplayer", nextplayer)}
    <mode>mode</mode>
    {boardToXML(board)}
    </gamestate>

    import java.io._
    val pw = new PrintWriter(new File("gamestate.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xmlFormat = prettyPrinter.format(xml)
    pw.write(xmlFormat)
    pw.close()
  }

  override def load(): (BoardComponent, Player, Player, String) = ???

  def playerToXML(playertype: String, player: Player): Elem = {
    <player>
      <role>{player.role}</role>
      <name>{player.name}</name>
      <stone>{player.stone.toString}</stone>
      <currentplayer>{playertype}</currentplayer>
    </player>
  }

   
   def boardToXML(board: BoardComponent): Elem = {
    val matrixXml = for {
      row <- 0 until board.getBoard.numRows
      col <- 0 until board.getBoard.numCols
    } yield {
      <cell>
        <row>{row}</row>
        <col>{col}</col>
        <color>{board.getStoneAt(row, col).toString}</color>
      </cell>
    }

    <matrix>{matrixXml}</matrix>
  }

  


}