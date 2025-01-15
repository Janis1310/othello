package de.htwg.se.othello.model.FileIOComponent

import de.htwg.se.othello.model.BoardComponents.BoardComponent

trait FileIOInterface {

    def save(board: BoardComponent) : Unit
    def load() : BoardComponent

}

