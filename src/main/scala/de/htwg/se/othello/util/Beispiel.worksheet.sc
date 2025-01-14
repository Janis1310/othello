import de.htwg.se.othello.model.Playercomponents.Player
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import play.api.libs.json._
val player = Player("Player1", Stone.Black, "Human")

val json = Json.toJson(player)



