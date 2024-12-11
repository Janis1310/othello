package de.htwg.se.othello.aview.GUI

import scala.swing._
import scala.swing.event.ButtonClicked
import de.htwg.se.othello.controller.Controller

class OthelloGUI(controller:Controller) extends MainFrame{
  title = "Othello"

   // Die Methode, um das Initialisierungs-Panel zu erstellen
  def createInitPanel = new BoxPanel(Orientation.Vertical) {
    // Hinzufügen eines Panels für die Eingabe der Spielernamen
    contents += new BoxPanel(Orientation.Horizontal) {
      val player1Field = new TextField { columns = 15 }
      val player2Field = new TextField { columns = 15 }
      contents += new Label("Player 1:")
      contents += player1Field
      contents += new Label("Player 2:")
      contents += player2Field
    
    }

    contents += new BoxPanel(Orientation.Vertical){
       preferredSize = new Dimension(300,20)

    }

    // Hinzufügen eines Panels für die Eingabe der Spielfeldgröße
    contents += new BoxPanel(Orientation.Horizontal) {
      val rowsField = new TextField { columns = 5 }
      val colsField = new TextField { columns = 5 }
      contents += new Label("Rows:")
      contents += rowsField
      contents += new Label("Columns:")
      contents += colsField
    }

  }

  val startButton = new Button("Start")
  


  reactions += {
    case event: ButtonClicked if event.source == startButton => println("Hallo")
      
  }
  
  centerOnScreen()  // Das Fenster wird zentriert
  visible = true

  def start(): Unit = {
    contents = createInitPanel


  }


}

  

