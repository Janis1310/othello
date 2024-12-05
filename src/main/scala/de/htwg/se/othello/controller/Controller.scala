package de.htwg.se.othello.controller
import de.htwg.se.othello.model.{Board, Stone, Stoneposition, MoveHandler, Player}
import de.htwg.se.othello.util.Observable
import scala.collection.immutable.Queue
import de.htwg.se.othello.model
import de.htwg.se.othello.model.handler.{MoveHandlerTemplate}



class Controller(private var board: Board) extends Observable {
  private var players: Queue[Player] = Queue()
  private var gameState: GameState.GameState = GameState.SETUP
  private var moveHandler: MoveHandlerTemplate = MoveHandler

  // Gibt das Board als Zeichenkette zurück
  def boardToString: String = board.toString

  // Spieler hinzufügen und den aktuellen Zustand zurückgeben
  def addPlayers(player1Name: String,player2Name: String): Unit = {
     if (gameState == GameState.SETUP) {
      players = Queue(
        Player(player1Name, Stone.White,"Human" ),
        Player(player2Name, Stone.Black, "Human")
      )
    } else {
      println("Spieler können nur im SETUP-Zustand hinzugefügt werden.")
    }
  }
  def getPlayers: Queue[Player] = players

  def getCurrentPlayer: Player = players.head

    def nextPlayer(): Unit = {
      val (currentPlayer, updatedQueue) = players.dequeue
      players = updatedQueue.enqueue(currentPlayer)
      gameState match {
      case GameState.WHITE_TURN => changeState(GameState.BLACK_TURN)
      case GameState.BLACK_TURN => changeState(GameState.WHITE_TURN)
      case _ => println("Spielerwechsel ist im aktuellen Zustand nicht möglich.")
    }
    
      notifyObservers
  }
  

  // Führt einen Zug aus und gibt den neuen Zustand des Spiels zurück
  def makeMove(x: Int, y: Int): Either[String, String] = {
     gameState match {
      case GameState.WHITE_TURN | GameState.BLACK_TURN =>
        val stone = getCurrentPlayer.stone
        val stonePosition = Stoneposition(x, y, stone)

        try {
          board = moveHandler.processMove(stonePosition, board) // Template-Methode aufrufen
          nextPlayer() // Nach einem gültigen Zug den Spieler wechseln
          notifyObservers
          Right(boardToString)
        } catch {
           case _: IllegalArgumentException => Left(" Ungültiger Zug.")
        }

      case _ =>
        Left("Züge sind nur während eines Spielzugs erlaubt.")
    }
  }
  // Erstellt ein neues Board und gibt es zurück
  def createNewBoard(rows: Int, cols: Int): Board = {
    if (gameState == GameState.SETUP) {
      board = new Board(rows, cols)
      changeState(GameState.WHITE_TURN) // Erst nach Erstellung des Boards den Zustand ändern
      notifyObservers
      board
    } else {
      println("Das Board kann nur im SETUP-Zustand erstellt werden.")
      board
    }
  }

   def changeState(newState: GameState.GameState): Unit = {
    gameState = newState
    GameState.action(gameState)
    //notifyObservers
  }

  def getGameState: GameState.GameState = gameState

}
