package de.htwg.se.othello.model
import de.htwg.se.othello.model.handler.{MoveHandlerInterface}

class ScoringMoveHandlerDecorator(innerHandler: MoveHandlerInterface) extends MoveHandlerInterface {
  private var score: Map[Stone, Int] = Map(Stone.White -> 2, Stone.Black -> 2)

  override def isValidMove(stonePosition: Stoneposition, board: Board): Boolean = {
    innerHandler.isValidMove(stonePosition, board) // Keine Änderung
  }

  override def flipStones(stonePosition: Stoneposition, board: Board): Board = {
    print("Halllllllllll")
    val updatedBoard = innerHandler.flipStones(stonePosition, board)

    // Umgedrehte Steine zählen
    val flippedCount = countFlippedStones(stonePosition, updatedBoard)
    
    // Score aktualisieren
    score = score.updated(stonePosition.stone, score(stonePosition.stone) + flippedCount)
    
    // Ausgabe des aktuellen Scores
    println(s"Aktueller Spielstand: Weiß = ${score(Stone.White)}, Schwarz = ${score(Stone.Black)}")

    updatedBoard
  }

  private def countFlippedStones(stonePosition: Stoneposition, board: Board): Int = {
   val allCells = board.getBoard.matrix.flatten

  // Zähle die Steine des aktuellen Spielers (weiß oder schwarz)
   val playerStoneCount = allCells.count(_ == stonePosition.stone)

  // Gib die Anzahl der Steine des Spielers zurück (z. B. Weiß oder Schwarz)
   playerStoneCount
}
}
