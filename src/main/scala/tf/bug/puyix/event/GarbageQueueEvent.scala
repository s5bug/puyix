package tf.bug.puyix.event

import tf.bug.puyix.game.board.{Garbage, GarbageType}

case class GarbageQueueEvent[A: GarbageType](g: Garbage[A]) extends GameEvent
