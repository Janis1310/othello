package de.htwg.se.othello.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayOutputStream, PrintStream}

class OthelloSpecs extends AnyWordSpec with Matchers {

  "The Othello main method" should {

    "print the 8x8 board correctly to the console" in {

      val outStream = new ByteArrayOutputStream()
      val printStream = new PrintStream(outStream)


      Console.withOut(printStream) {
        Othello.main(Array.empty)
      }


      val printedOutput = outStream.toString
      print(printedOutput)


      val expectedOutput = """
            0   1   2   3   4   5   6   7
          +---+---+---+---+---+---+---+---+
        0 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        1 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        2 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        3 | . | . | . | W | S | . | . | . |
          +---+---+---+---+---+---+---+---+
        4 | . | . | . | S | W | . | . | . |
          +---+---+---+---+---+---+---+---+
        5 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        6 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+
        7 | . | . | . | . | . | . | . | . |
          +---+---+---+---+---+---+---+---+ """.stripMargin

      print(expectedOutput)
      printedOutput.replaceAll("\\s+", "") shouldBe expectedOutput.replaceAll("\\s+", "")
    }
  }
}