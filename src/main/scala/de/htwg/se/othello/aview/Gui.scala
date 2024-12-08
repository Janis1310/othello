package de.htwg.se.othello.aview

import scalafx.application.JFXApp3
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.VBox
import scalafx.scene.text.Text
import scalafx.geometry.Insets

object Gui extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Simple ScalaFX UI"
      scene = new Scene {
        root = new VBox {
          spacing = 10
          padding = Insets(20)
          children = Seq(
            new Text {
              text = "Willkommen zu ScalaFX!"
              style = "-fx-font-size: 16pt"
            },
            new Button("Klick mich!") {
              onAction = _ => println("Button wurde geklickt!")
            }
          )
        }
      }
    }
  }
}