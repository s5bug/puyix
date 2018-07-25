package tf.bug.puyix.game.board

import spire.math.UInt

case class Garbage[A: GameMode](amount: UInt)(implicit garbageType: GarbageType[A])

trait GarbageType[A]
