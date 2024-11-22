package de.htwg.se.othello.util
import java.security.KeyStore.TrustedCertificateEntry
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
class ObservableSpec extends AnyWordSpec {
  "An Observable" should {
    val observable = new Observable
    val observer = new Observer {
      var updated: Boolean = false
      def isUpdated: Boolean = updated
      override def update: Unit = {updated = true}
    }
    "add an Observer" in {
      observable.add(observer)
      observable.subscribers should contain (observer)
    }    


    "remove an Observer" in {
      observable.remove(observer)
      observable.subscribers should not contain (observer)
    }

  }

}