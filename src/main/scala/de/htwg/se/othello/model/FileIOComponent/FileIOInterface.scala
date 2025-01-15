package de.htwg.se.othello.model.FileIOComponent

import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.Playercomponents.Player

trait FileIOInterface {

    def save(board: BoardComponent, curretplayer: Player, nextplayer: Player, mode: String) : Unit
    def load() : BoardComponent

}

