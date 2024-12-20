package de.htwg.se.othello.util
import java.security.KeyStore.TrustedCertificateEntry
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
class ObservableSpec extends AnyWordSpec {
  class TestObserver extends Observer {
    var updated: Boolean = false
  
    override def update: Unit = { updated = true }
  }


  "An Observable" should {
    val observable = new Observable
    val observer = new TestObserver
    "add an Observer" in {
      observable.add(observer)
      observable.subscribers should contain (observer)
    }    


    "remove an Observer" in {
      observable.remove(observer)
      observable.subscribers should not contain (observer)
    }

    "notify all its Observers" in {
      val observer1 = new TestObserver
      val observer2 = new TestObserver

      // Add observers to the observable
      observable.add(observer1)
      observable.add(observer2)

      // Notify all observers
      observable.notifyObservers

      // Assert that all observers were notified
      observer1.updated should be(true)
      observer2.updated should be(true)
    }


  }

}