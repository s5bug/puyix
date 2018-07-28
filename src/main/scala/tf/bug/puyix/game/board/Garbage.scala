package tf.bug.puyix.game.board

import spire.math.UInt

/**
  * See [[https://puyonexus.com/wiki/Scoring Puyo Puyo Scoring]] and [[http://tetris.wikia.com/wiki/Scoring Tetris Scoring]]
  *
  * @param score The score created
  * @param garbageType The garbageType adapter
  * @tparam A The board the garbage comes from. */
case class Garbage[A: GarbageType](score: UInt)(implicit val garbageType: GarbageType[A])

trait GarbageType[A]
