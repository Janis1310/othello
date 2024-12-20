package de.htwg.se.othello.model.Interface

import de.htwg.se.othello.model.Stone

trait StonepositionComponent {
    def x: Int
    def y: Int
    def stone: StoneComponent
}
