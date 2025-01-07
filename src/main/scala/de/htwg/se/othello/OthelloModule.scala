package de.htwg.se.othello

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.othello.model.BoardComponents.BoardComponent
import de.htwg.se.othello.model.BoardComponents.MatrixInterface
import de.htwg.se.othello.model.BoardComponents.StoneComponent
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Matrix
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone.Stone
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Board
import de.htwg.se.othello.controller.ControllerComponents.ControllerComponent
import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl.Controller

class OthelloModule extends AbstractModule with ScalaModule{

    override def configure() = {
        bind[MatrixInterface[StoneComponent]].to[Matrix[StoneComponent]]
        bind[BoardComponent].to[Board]
        bind[ControllerComponent].to[Controller]
    }
    

}
