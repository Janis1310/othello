package de.htwg.se.othello.model

object moveValidator {
    def isValidMove(x: Int, y: Int, color: Stone, board: Board): Boolean = {
        if (board.getStoneAt(x,y) != Stone.Empty) { //field is empty?
            println("field is not empty")
            return false
        }

        if (board.getStoneAt(x+1,y) != color.flip) { // das müssen wir noch rinksrum prüfen
            println(board.getStoneAt(x+1,y))
            println("field is not flippable")
            return false
        }

        // Prüfung fehlt noch
        return true
    }
}
