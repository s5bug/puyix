package tf.bug.puyix.tetris

import java.util.concurrent.TimeUnit
import org.scalatest._
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration.FiniteDuration
import spire.math.UInt
import tf.bug.puyix.event.GarbageQueueEvent
import tf.bug.puyix.game.board.Garbage

class TetrisSpec extends WordSpec {

  def qa[T](f: Future[T]): T = Await.result(f, FiniteDuration(1, TimeUnit.MINUTES))

  "Tetris" when {
    "sending garbage points < 200" should {
      "generate no lines" in {
        val tetris = new Tetris
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(0)))))
        assert(tetris.getGarbageQueue.isEmpty)
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(1)))))
        assert(tetris.getGarbageQueue.isEmpty)
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(100)))))
        assert(tetris.getGarbageQueue.isEmpty)
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(150)))))
        assert(tetris.getGarbageQueue.isEmpty)
      }
    }
    "sending garbage points at multiples of 200" should {
      "generate n/200 lines" in {
        val tetris = new Tetris
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(200)))))
        assertResult(1)(tetris.getGarbageQueue.sum)
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(400)))))
        assertResult(3)(tetris.getGarbageQueue.sum)
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(600)))))
        assertResult(6)(tetris.getGarbageQueue.sum)
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(800)))))
        assertResult(10)(tetris.getGarbageQueue.sum)
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(1000)))))
        assertResult(15)(tetris.getGarbageQueue.sum)
        qa (tetris.onNext(GarbageQueueEvent(Garbage[Tetris](UInt(2000)))))
        assertResult(25)(tetris.getGarbageQueue.sum)
      }
    }
  }

}
