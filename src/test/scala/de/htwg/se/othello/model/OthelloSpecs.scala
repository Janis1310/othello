package de.htwg.se.othello.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import java.io.{ByteArrayOutputStream, PrintStream}

class OthelloSpecs extends AnyWordSpec with Matchers {

  "The Othello main method" should {

    "print the 8x8 board correctly to the console" in {
      // Erstelle einen ByteArrayOutputStream, um die Ausgabe zu speichern
      val outStream = new ByteArrayOutputStream()
      val printStream = new PrintStream(outStream)

      // Umleiten der Ausgabe mit Console.withOut
      Console.withOut(printStream) {
        Othello.main(Array.empty) // Aufruf der main-Methode
      }

      // Die tatsächliche Ausgabe abrufen
      val printedOutput = outStream.toString
      print(printedOutput)

      // Erwartete Ausgabe für das 8x8-Board dynamisch erzeugen
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