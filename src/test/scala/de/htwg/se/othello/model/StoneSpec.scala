package de.htwg.se.othello.model

import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class StoneSpec extends AnyWordSpec with Matchers {

  "A Stone" should {

    "be Black when created as Black" in {
      val stone = Stone.Black
      stone shouldBe Stone.Black
    }

    "be White when created as White" in {
      val stone = Stone.White
      stone shouldBe Stone.White
    }

    "be Empty when created as Empty" in {
      val stone = Stone.Empty
      stone shouldBe Stone.Empty
    }

    "have correct toString value for Black" in {
      val stone = Stone.Black
      stone.toString shouldBe "S"
    }

    "have correct toString value for White" in {
      val stone = Stone.White
      stone.toString shouldBe "W"
    }

    "have correct toString value for Empty" in {
      val stone = Stone.Empty
      stone.toString shouldBe "."
    }

    "flip from Black to White" in {
      val stone = Stone.Black
      stone.flip shouldBe Stone.White
    }

    "flip from White to Black" in {
      val stone = Stone.White
      stone.flip shouldBe Stone.Black
    }
    "throw an UnsupportedOperationException when flip is called on Empty" in {
      val stone = Stone.Empty
      assertThrows[UnsupportedOperationException] {
        stone.flip
      }
    }

  }
}
