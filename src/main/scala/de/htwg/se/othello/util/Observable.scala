package de.htwg.se.othello.util

trait Observer {
  def update: Unit // every Observer must implement this method
}

class Observable {
  var subscribers: Vector[Observer] = Vector() // vector of observers objects, waiting for updates

  def add(s: Observer): Unit = subscribers = subscribers :+ s // add an observer to the subscribers vector

  def remove(s: Observer): Unit = subscribers = subscribers.filterNot(o => o == s) // remove an observer from the subscribers vector

  def notifyObservers: Unit = subscribers.foreach(o => o.update) // update and notify all observers
}