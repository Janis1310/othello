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
import de.htwg.se.othello.model.CommandComponents.CommandBaseImpl.UndoManager
import de.htwg.se.othello.model.CommandComponents.UndoManagerComponent
import de.htwg.se.othello.model.HandlerComponents.MoveHandlerTemplateInterface
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandler
import de.htwg.se.othello.model.HandlerComponents.HandlerBaseImpl.MoveHandlerTemplate
import com.google.inject.Provides
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone.EmptyStone

class OthelloModule extends AbstractModule with ScalaModule{

        @Provides
        def provideMatrix(): Matrix[StoneComponent] = {
        new Matrix[StoneComponent](8, 8, EmptyStone) // Beispiel für eine Matrix mit 8x8 Feldern und dem Standardwert Empty
    }

        override def configure() = {
            bind(classOf[MatrixInterface[StoneComponent]]).to(classOf[Matrix[StoneComponent]])
            bind(classOf[UndoManagerComponent]).to(classOf[UndoManager])
            bind(classOf[MoveHandlerTemplateInterface]).toInstance(MoveHandler)
            bind[BoardComponent].toInstance(new Board(8, 8))
            bind(classOf[ControllerComponent]).to(classOf[Controller])

        }
    

}
