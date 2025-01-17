package de.htwg.se.othello.aview.GUI

import de.htwg.se.othello.controller.ControllerComponents.ControllerBaseImpl.Controller
import scala.swing._
import scala.swing.Swing._
import scala.swing.event.ButtonClicked
import de.htwg.se.othello.model.BoardComponents.BoardBaseImpl.Stone
import scala.util.Try
import java.awt.Font
import java.awt.Color
import javax.swing.ImageIcon
import javax.swing.BorderFactory
import de.htwg.se.othello.util.Observer
import de.htwg.se.othello.controller.ControllerComponents.ControllerComponent
import de.htwg.se.othello.controller.ControllerComponents.GameState
import scala.annotation.constructorOnly
import javax.swing.SwingUtilities
import scala.util.Success
private val backgroundColor = new Color(65,100,40)
private val foregroundColor = Color.WHITE

class OthelloGUI(controller:ControllerComponent) extends MainFrame with Observer{
  controller.add(this)
  title = "Othello"
  peer.setLocationRelativeTo(null)
  resizable = false

  val white_stone = new ImageIcon("src/main/resources/pieces/white.png")
  val black_stone = new ImageIcon("src/main/resources/pieces/black.png")

  def chooseMode: BoxPanel = {
    val titelLabel = new Label("Willkommen zum Spiel Othello") {
      font = new Font("Arial", Font.BOLD, 24)
      foreground = foregroundColor
    }
    val modeLabel = new Label("Wählen Sie den Spielmodus:") {
      font = new Font("Arial", Font.BOLD, 16)
      horizontalAlignment = Alignment.Left
      foreground = foregroundColor
    }
    val playerButton = new RadioButton("Gegen Mensch spielen"){
      background = backgroundColor
      font = new Font("Arial", Font.BOLD, 16)
      foreground = foregroundColor
    }
    val aiButton = new RadioButton("Gegen KI spielen") {
      background = backgroundColor
      font = new Font("Arial", Font.BOLD, 16)
      foreground = foregroundColor
    }
    val loadButton = new RadioButton("Load"){
      background = backgroundColor
      font = new Font("Arial", Font.BOLD, 16)
      foreground = foregroundColor
    }
    val buttonGroup = new ButtonGroup(playerButton, aiButton, loadButton)
    val confirmButton = new Button("Weiter"){
      background = backgroundColor
      font = new Font("Arial", Font.BOLD, 16)
      foreground = foregroundColor
      border = BorderFactory.createLineBorder(foregroundColor, 3)
    }

    val radioPanel = new BoxPanel(Orientation.Horizontal) {
      background = backgroundColor
      contents += playerButton
      contents += HStrut(10) // Horizontaler Abstand zwischen den Radiobuttons
      contents += aiButton
      contents += HStrut(10)
      contents += loadButton
      }

    val buttonPanel = new FlowPanel{
      background = backgroundColor
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
        } else if (loadButton.selected){
          controller.load()
        }else {
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
      contents += new FlowPanel(FlowPanel.Alignment.Left)(titelLabel){
        background = backgroundColor
      }

      contents += VStrut(20) // Vertikaler Abstand zwischen Titel und Radiobuttons
      contents += new FlowPanel(FlowPanel.Alignment.Left)(modeLabel){
        background = backgroundColor
      }
      contents += VStrut(10) // Vertikaler Abstand
      contents += radioPanel
      contents += VStrut(20) // Vertikaler Abstand
      contents += buttonPanel
      border = EmptyBorder(20, 20, 20, 20)
      background = backgroundColor

    }
}



   // Die Methode, um das Initialisierungs-Panel zu erstellen
  def initBoard = new BoxPanel(Orientation.Vertical) {
    background = backgroundColor
    val player1Field = new TextField {
      columns = 15
      background = backgroundColor
      foreground = foregroundColor
      border = BorderFactory.createLineBorder(foregroundColor, 3)
    }
    val player2Field = new TextField {
      columns = 15
      background = backgroundColor
      foreground = foregroundColor
      border = BorderFactory.createLineBorder(foregroundColor, 3)
    }

    if (controller.getGameMode == "Human") {

      contents += new BoxPanel(Orientation.Horizontal) {
        background = backgroundColor

        contents += new Label("Player 1:"){
          foreground = foregroundColor
        }
        contents += player1Field
        contents += new Label("Player 2:"){
          foreground = foregroundColor
        }
        contents += player2Field
    
    }

    } else {
      contents += new BoxPanel(Orientation.Horizontal) {
        background = backgroundColor
        contents += new Label("Player Name:"){
          foreground = foregroundColor
        }
        contents += player1Field
    }
    }


    contents += new BoxPanel(Orientation.Vertical){
       preferredSize = new Dimension(300,20)
       background = backgroundColor
    }
    val rowsField = new TextField {
      columns = 5
      background = backgroundColor
      border = BorderFactory.createLineBorder(foregroundColor, 3)
    }
    val colsField = new TextField {
      background = backgroundColor
      columns = 5
      border = BorderFactory.createLineBorder(foregroundColor, 3)
    }

    // Hinzufügen eines Panels für die Eingabe der Spielfeldgröße
    contents += new BoxPanel(Orientation.Horizontal) {
      
      background = backgroundColor
      contents += new Label("Rows:"){
        foreground = foregroundColor
      }
      contents += rowsField
      contents += new Label("Columns:"){
        foreground = foregroundColor
      }
      contents += colsField
    }

    val button = new Button("Start"){
      Alignment.Center
      background = backgroundColor
      foreground = foregroundColor
      font = new Font("Arial", Font.BOLD, 16)
      border = BorderFactory.createLineBorder(foregroundColor, 3)
    }
    listenTo(button)

    contents += new BoxPanel(Orientation.Vertical){
    contents += button
    background = backgroundColor
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
      layout(new Label(s"${controller.getCurrentPlayer.name} ist dran, Deine Farbe ist: ${controller.getCurrentPlayer.stone}")) = BorderPanel.Position.North

        // Spielfeld als zentrales Element
        layout(new GridPanel(controller.getBoard.getBoard.numRows, controller.getBoard.getBoard.numCols) {
          for (row <- 0 until controller.getBoard.getBoard.numRows; col <- 0 until controller.getBoard.getBoard.numCols) {
            val cellValue = controller.getBoard.getBoard.cell(row, col) // Zelleninhalt abrufen
            val button = new Button {
              text = " "
              preferredSize = new Dimension(50, 50)
              border = BorderFactory.createLineBorder(Color.BLACK, 1)
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

        layout(new BoxPanel(Orientation.Vertical) {
          val (w, s) = controller.countStone()
          preferredSize = new Dimension(210, 200)
          background = backgroundColor

          contents += Swing.VStrut(80)
           contents += new Label {
            text = s"Punktestand => W: $w | S: $s"
            font = new Font("Arial", Font.BOLD, 14)
            foreground = foregroundColor
          }
          contents += Swing.VStrut(20)
           contents += new Button("Undo") {
            maximumSize = new Dimension(200, 30)
            foreground = foregroundColor
            background = backgroundColor
            border = BorderFactory.createLineBorder(foregroundColor, 3)
            reactions += {
               case ButtonClicked(_) =>
                controller.undo
              }
            }
          contents += Swing.VStrut(10)

          contents += new Button("Redo") {
            maximumSize = new Dimension(200, 30)
            background = backgroundColor
            foreground = foregroundColor
            border = BorderFactory.createLineBorder(foregroundColor, 3)
            reactions += {
              case ButtonClicked(_) =>
                controller.redo
              }
            }
          contents += Swing.VStrut(10)

          contents += new Button("Save and Quit") {
            maximumSize = new Dimension(200, 30)
            background = backgroundColor
            foreground = foregroundColor
            border = BorderFactory.createLineBorder(foregroundColor, 3)
            reactions += {
              case ButtonClicked(_) =>
                controller.save()
                System.exit(0)
              }
            }


          }) = BorderPanel.Position.East
        }
  
  centerOnScreen()
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

  

