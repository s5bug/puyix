package tf.bug.puyix.tetris

import java.security.SecureRandom
import java.util.concurrent.ConcurrentLinkedDeque

import monix.execution.{Ack, Cancelable}
import monix.reactive.observers.Subscriber
import spire.math.UInt
import tf.bug.puyix.event.{GameEvent, GarbageQueueEvent}
import tf.bug.puyix.game.board.GameMode

import scala.collection.JavaConverters._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

class Tetris extends GameMode {

  private val garbageQueue: ConcurrentLinkedDeque[Int] = new ConcurrentLinkedDeque[Int]()

  def getGarbageQueue: Seq[Int] = garbageQueue.asScala.toSeq

  override def onNext(elem: GameEvent): Future[Ack] = Future {
    elem match {
      case GarbageQueueEvent(g) =>
        def garbageDivisions = Stream.iterate(0)(n => if(SecureRandom.getInstanceStrong.nextInt(10) < 3) n + 1 else n)
        val garbageLines = (g.score / UInt(200)).toLong
        val groups = garbageDivisions.take(garbageLines.toInt).toSeq.groupBy(i => i).values.map(_.size)
        groups.foreach(garbageQueue.add)
    }
    Ack.Continue
  }(ExecutionContext.global)

  override def onError(ex: Throwable): Unit = ???

  override def onComplete(): Unit = {}

  override def unsafeSubscribeFn(subscriber: Subscriber[GameEvent]): Cancelable = ???

}
