package tf.bug.puyix.game.board

import spire.math.UInt

case class Garbage[A: GarbageType](amount: UInt)(implicit garbageType: GarbageType[A])

trait GarbageType[A]
