package de.htwg.se.othello.aview

import de.htwg.se.othello.controller.Controller
import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.scene.text.Text
import scalafx.geometry.Insets


import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.BorderPane

object GuiMain extends JFXApp3 {
  var controller : Option[Controller]= None
  def setController(controller: Controller): Unit = {
    this.controller = Some(controller)
  }
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title.value = "Othello"
      scene = new Scene() {
        root = new VBox {
          spacing = 50
          children = Seq(
            new Button ("Button 1"),
            new Button ("Button 2")
          )
        }
      }
      fullScreen = true
    }
  }
}