package de.htwg.se.othello.aview.GUI

import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl.Controller
import scala.swing._
import scala.swing.Swing._
import scala.swing.event.ButtonClicked
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import scala.util.Try
import java.awt.Font
import javax.swing.ImageIcon
import de.htwg.se.othello.util.Observer
import de.htwg.se.othello.controller.ControllerComponents.ControllerComponent
import de.htwg.se.othello.controller.ControllerComponents.GameState
import scala.annotation.constructorOnly
import javax.swing.SwingUtilities
import scala.util.Success

class OthelloGUI(controller:ControllerComponent) extends MainFrame with Observer{
  controller.add(this)
  title = "Othello"
  peer.setLocationRelativeTo(null)

  val white_stone = new ImageIcon("src/main/resources/pieces/white.png")
  val black_stone = new ImageIcon("src/main/resources/pieces/black.png")

  def chooseMode: BoxPanel = {

    val titelLabel = new Label("Willkommen zum Spiel Othello") { font = new Font("Arial", Font.BOLD, 24)}
     val modeLabel = new Label("Wählen Sie den Spielmodus:") {
      font = new Font("Arial", Font.BOLD, 16)
      horizontalAlignment = Alignment.Left // Links ausrichten
    }
    val playerButton = new RadioButton("Gegen Mensch spielen"){
      font = new Font("Arial", Font.BOLD, 16)
    }
    val aiButton = new RadioButton("Gegen KI spielen") {
      font = new Font("Arial", Font.BOLD, 16)
    }
    val buttonGroup = new ButtonGroup(playerButton, aiButton)
    val confirmButton = new Button("Weiter"){
      font = new Font("Arial", Font.BOLD, 16)
    }

    val radioPanel = new BoxPanel(Orientation.Horizontal) {
        contents += playerButton
        contents += HStrut(10) // Horizontaler Abstand zwischen den Radiobuttons
        contents += aiButton
      }

    val buttonPanel = new FlowPanel{
      contents += confirmButton
    }




  // Reaktion auf den Buttonklick
    listenTo(confirmButton)
    reactions += {
      case ButtonClicked(confirmButton) =>
        if (playerButton.selected) {
          println("Spielmodus: Gegen Mensch")
          controller.setGameMode("Human")
          controller.changeState(GameState.InputPlayer1)
          contents = initBoard
        } else if (aiButton.selected) {
          println("Spielmodus: Gegen KI")
          controller.setGameMode("AI")
          controller.changeState(GameState.InputPlayer1)
          contents = initBoard
        } else {
          Dialog.showMessage(
            null,
            "Bitte wählen Sie einen Spielmodus aus!",
            "Auswahl erforderlich",
            Dialog.Message.Error
          )
        }
    }

    // Layout
    new BoxPanel(Orientation.Vertical) {
      contents += titelLabel
      contents += VStrut(20) // Vertikaler Abstand zwischen Titel und Radiobuttons
      contents += new FlowPanel(FlowPanel.Alignment.Left)(modeLabel)
      contents += VStrut(10) // Vertikaler Abstand
      contents += radioPanel
      contents += VStrut(20) // Vertikaler Abstand
      contents += buttonPanel
      border = EmptyBorder(20, 20, 20, 20)
      
    }
}



   // Die Methode, um das Initialisierungs-Panel zu erstellen
  def initBoard = new BoxPanel(Orientation.Vertical) {
    val player1Field = new TextField { columns = 15 }
    val player2Field = new TextField { columns = 15 }

    if (controller.getGameMode == "Human") {

      contents += new BoxPanel(Orientation.Horizontal) {
      
      contents += new Label("Player 1:")
      contents += player1Field
      contents += new Label("Player 2:")
      contents += player2Field
    
    }

    } else {
      contents += new BoxPanel(Orientation.Horizontal) {
      contents += new Label("Player Name:")
      contents += player1Field
    }
    }


    contents += new BoxPanel(Orientation.Vertical){
       preferredSize = new Dimension(300,20)

    }
    val rowsField = new TextField { columns = 5 }
    val colsField = new TextField { columns = 5 }

    // Hinzufügen eines Panels für die Eingabe der Spielfeldgröße
    contents += new BoxPanel(Orientation.Horizontal) {
      
      contents += new Label("Rows:")
      contents += rowsField
      contents += new Label("Columns:")
      contents += colsField
    }

    val button = new Button("Start"){
      Alignment.Center

    }
    listenTo(button)

    contents += new BoxPanel(Orientation.Vertical){
    contents += button
  }
    reactions +={
      case ButtonClicked(button) =>
        val player1 = if(player1Field.text.nonEmpty) player1Field.text else "Player 1"
        val player2 = if(player2Field.text.nonEmpty) player2Field.text else "Player 2"
        
        val rows = Try(rowsField.text.toInt).getOrElse(8)
        val cols = Try(colsField.text.toInt).getOrElse(8)

        
        if (controller.getGameState == GameState.InputPlayer1) {
          controller.addPlayers(player1)
          if (controller.getGameMode == "AI") {
            controller.changeState(GameState.InputBoardSize)
          }
        }
          
          
        if (controller.getGameState == GameState.InputPlayer2) {
          controller.addPlayers(player2)
          controller.changeState(GameState.InputBoardSize)
        }
        

        controller.getGameState match {
          case GameState.InputBoardSize =>

          if (rows > 0 && cols > 0) {
            controller.createNewBoard(rows, cols) // Neues Spielfeld erstellen
            println(s"Spiel gestartet mit $player1 und $player2 auf einem $rows x $cols Feld.")
            controller.notifyObservers

            
          } else {
            Dialog.showMessage(
              this,
              "Bitte geben Sie gültige Werte für die Anzahl der Zeilen und Spalten ein.",
              "Ungültige Eingabe",
              Dialog.Message.Error
            )
          }
        }
    }

  }

    def createboard: BorderPanel = new BorderPanel {
        // Obere Statusanzeige
        layout(new Label(s"${controller.getCurrentPlayer.name} ist dran, Deine Farbe ist: ${controller.getCurrentPlayer.stone}")) = BorderPanel.Position.North

        // Spielfeld als zentrales Element
        layout(new GridPanel(controller.getBoard.getBoard.numRows, controller.getBoard.getBoard.numCols) {
          for (row <- 0 until controller.getBoard.getBoard.numRows; col <- 0 until controller.getBoard.getBoard.numCols) {
            val cellValue = controller.getBoard.getBoard.cell(row, col) // Zelleninhalt abrufen
            val button = new Button {
              text = " " // Text bleibt leer
              preferredSize = new Dimension(50, 50)

              // Hintergrundfarbe je nach Zellenwert setzen
              cellValue match {
                case Stone.Empty => background = new Color(65,100,40) // Leeres Feld
                case Stone.Black => icon = black_stone; background = new Color(65,100,40)  // Schwarzer Stein
                case Stone.White => icon = white_stone; background = new Color(65,100,40) // Weißer Stein
              }
            }
            listenTo(`button`)
            reactions += {
              case ButtonClicked(`button`) =>
                val result = controller.makeMove(row, col)

                if (result) {
                  controller.notifyObservers
                  if (controller.getCurrentPlayer.role == "AI")
                    SwingUtilities.invokeLater(new Runnable {
                      override def run(): Unit = {

                        Thread.sleep(1000)
                        controller.processAITurn()
                      }
                    })

                
                } else {
                  Dialog.showMessage(this, "Ungültiger Zug", "Fehlermeldung", Dialog.Message.Error)
                }

            }

            contents += button
          }
        }) = BorderPanel.Position.Center
  }
  
  centerOnScreen()  // Das Fenster wird zentriert
  visible = true

  def start(): Unit = {
    contents = chooseMode
  }

  def refreshBoard(): Unit = { 
    contents = createboard 
    validate() // Aktualisiert das Layout repaint() // Zeichnet die GUI neu
    repaint()
}

  override def update: Unit = {
      refreshBoard()
        
      //println(controller.boardToString) // Das brauchen wir nicht, oder?
      
    }

}

  

