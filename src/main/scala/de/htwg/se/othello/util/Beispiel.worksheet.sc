import de.htwg.se.othello.model.Playercomponents.Player
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import play.api.libs.json._
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board
import de.htwg.se.othello.model.FileIOComponent.FileIoXML.FileIO
val player = Player("Player1", Stone.Black, "Human")

val json = Json.toJson(player)



val board: BoardComponent = new Board(8, 8) // Beispielbrett
val currentPlayer = Player("Alice", Stone.White, "Human")
val nextPlayer = Player("Bob", Stone.Black, "Human")
val mode = "classic"

val fileIO = new FileIO
fileIO.save(board, currentPlayer, nextPlayer, mode)



// Überprüfung: Inhalt von "gamestate.json" manuell prüfen.
