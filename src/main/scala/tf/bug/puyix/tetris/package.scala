package tf.bug.puyix

import monix.execution.{Ack, Cancelable}
import monix.reactive.observers.Subscriber
import tf.bug.puyix.event.GameEvent
import tf.bug.puyix.game.board.{GameMode, Garbage, GarbageType}

import scala.concurrent.Future

package object tetris {

  implicit def tetrisGarbage: GarbageType[Tetris] = new GarbageType[Tetris] {}

}
