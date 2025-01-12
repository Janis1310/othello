package de.htwg.se.othello.ai

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.othello.ai.StrategyContext
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.{Board, Stone, Stoneposition}
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandler
import de.htwg.se.othello.model.Playercomponents.Player
import scala.collection.immutable.Queue

class StrategyContextSpec extends AnyWordSpec with Matchers {

  "StrategyContext" should {
    "set and get players correctly" in {
      val player1 = Player("Player1", Stone.Black)
      val player2 = Player("Player2", Stone.White)
      val players = Queue(player1, player2)

      StrategyContext.setPlayers(players)
      StrategyContext.getPlayers shouldBe players
      StrategyContext.getCurrentPlayer shouldBe player1
    }

    "execute strategy1 correctly" in {
      val player = Player("Player1", Stone.Black)
      val board = mock[BoardComponent]
      when(board.numRows).thenReturn(8)
      when(board.numCols).thenReturn(8)
      when(MoveHandler.isValidMove(Stoneposition(0, 0, Stone.Black), board)).thenReturn(true)

      StrategyContext.setPlayers(Queue(player))
      val result = StrategyContext.strategy1(board)

      result shouldBe Some(Stoneposition(0, 0, Stone.Black))
    }

    "execute strategy2 correctly" in {
      val player = Player("Player1", Stone.Black)
      val board = mock[BoardComponent]
      when(board.numRows).thenReturn(8)
      when(board.numCols).thenReturn(8)
      when(MoveHandler.isValidMove(Stoneposition(7, 7, Stone.Black), board)).thenReturn(true)

      StrategyContext.setPlayers(Queue(player))
      val result = StrategyContext.strategy2(board)

      result shouldBe Some(Stoneposition(7, 7, Stone.Black))
    }

    "return None if no valid moves in strategy1" in {
      val player = Player("Player1", Stone.Black)
      val board = mock[BoardComponent]
      when(board.numRows).thenReturn(8)
      when(board.numCols).thenReturn(8)
      when(MoveHandler.isValidMove(any[Stoneposition], eqTo(board))).thenReturn(false)

      StrategyContext.setPlayers(Queue(player))
      val result = StrategyContext.strategy1(board)

      result shouldBe None
    }

    "return None if no valid moves in strategy2" in {
      val player = Player("Player1", Stone.Black)
      val board = mock[BoardComponent]
      when(board.numRows).thenReturn(8)
      when(board.numCols).thenReturn(8)
      when(MoveHandler.isValidMove(any[Stoneposition], eqTo(board))).thenReturn(false)

      StrategyContext.setPlayers(Queue(player))
      val result = StrategyContext.strategy2(board)

      result shouldBe None
    }
  }
}
