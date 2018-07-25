package tf.bug.puyix.game.board

import monix.reactive.{Observable, Observer}
import tf.bug.puyix.event.GameEvent

trait GameMode extends Observable[GameEvent] with Observer[GameEvent]

trait GameModeLike[A] {

  def asGameMode(a: A): GameMode

}
