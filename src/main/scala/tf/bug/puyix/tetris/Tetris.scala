package tf.bug.puyix.tetris

import monix.execution.{Ack, Cancelable}
import monix.reactive.observers.Subscriber
import tf.bug.puyix.event.GameEvent
import tf.bug.puyix.game.board.GameMode

import scala.concurrent.Future

class Tetris extends GameMode {

  override def onNext(elem: GameEvent): Future[Ack] = ???

  override def onError(ex: Throwable): Unit = ???

  override def onComplete(): Unit = ???

  override def unsafeSubscribeFn(subscriber: Subscriber[GameEvent]): Cancelable = ???

}
