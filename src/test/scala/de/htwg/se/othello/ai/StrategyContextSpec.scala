package de.htwg.se.othello.ai

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.othello.ai.StrategyContext
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone, Stoneposition}
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandler
import de.htwg.se.othello.model.Playercomponents.Player

import scala.collection.immutable.Queue
import scala.util.Random

class StrategyContextSpec extends AnyWordSpec with Matchers {

  "StrategyContext" should {

    val player1 = Player("Player1", Stone.Black, "AI")
    val player2 = Player("Player2", Stone.White, "Human")
    val players = Queue(player1, player2)

    Player.setPlayers(players)


    "execute strategy1 and strategy2 correctly" in {
      val player = Player("Player1", Stone.Black, "AI")
      val testBoard = new Board(8, 8)
      val strategy1 = StrategyContext.strategy1(testBoard)
      strategy1 shouldBe (Some(Stoneposition(2, 3, Stone.Black)))

      val strategy2 = StrategyContext.strategy2(testBoard)
      strategy2 shouldBe (Some(Stoneposition(5, 4, Stone.Black)))
    }
  }
}

/* "select randomly either strategy1 or strategy2" in {
   val strategy = StrategyContext.strategy
   if (strategy == StrategyContext.strategy1) {
     strategy shouldBe (StrategyContext.strategy1)
   } else {
     strategy shouldBe (StrategyContext.strategy2)
   }
 }

}


"select randomly either strategy1 or strategy2" in {
 var strategy1Chosen = false
 var strategy2Chosen = false

 for (_ <- 1 to 100) {
   val chosenStrategy = StrategyContext.strategy
   println(f"chosenStrategy: $chosenStrategy, StrategyContext.strategy1: ${StrategyContext.strategy1}")
   if (chosenStrategy == StrategyContext.strategy1) strategy1Chosen = true
   if (chosenStrategy == StrategyContext.strategy2) strategy2Chosen = true
 }

 strategy1Chosen shouldBe true
 strategy2Chosen shouldBe true
}*/



