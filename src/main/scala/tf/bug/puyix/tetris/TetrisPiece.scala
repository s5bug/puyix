package tf.bug.puyix.tetris

import tf.bug.puyix.game.board.GarbageType

sealed trait TetrisBlock

case class TetrisGarbage[A]()(implicit val garbageType: GarbageType[A]) extends TetrisBlock

sealed trait TetrisPiece extends TetrisBlock {

  val f = false
  val t = true

  /**
    * The size of the bounding box of the piece.
    * 
    * $ - For `I` this is 4
    * $ - For `T`, `S`, `Z`, `J`, `L` this is 3
    * $ - For `O` this is 2 */
  val gridSize: Int

  /**
    * In spawn position, where blocks are located. */
  val spawnBlocks: Array[Array[Boolean]]

}

case object TetrisI extends TetrisPiece {

  override val gridSize = 4
  override val spawnBlocks = Array(
    Array(f, f, f, f),
    Array(t, t, t, t),
    Array(f, f, f, f),
    Array(f, f, f, f)
  )

}
case object TetrisO extends TetrisPiece {

  override val gridSize = 2
  override val spawnBlocks = Array(
    Array(t, t),
    Array(t, t)
  )

}
case object TetrisT extends TetrisPiece {

  override val gridSize = 3
  override val spawnBlocks = Array(
    Array(f, t, f),
    Array(t, t, t),
    Array(f, f, f)
  )

}
case object TetrisS extends TetrisPiece {

  override val gridSize = 3
  override val spawnBlocks = Array(
    Array(f, t, t),
    Array(t, t, f),
    Array(f, f, f)
  )

}
case object TetrisZ extends TetrisPiece {

  override val gridSize = 3
  override val spawnBlocks = Array(
    Array(t, t, f),
    Array(f, t, t),
    Array(f, f, f)
  )

}
case object TetrisJ extends TetrisPiece {

  override val gridSize = 3
  override val spawnBlocks = Array(
    Array(t, f, f),
    Array(t, t, t),
    Array(f, f, f)
  )

}
case object TetrisL extends TetrisPiece {

  override val gridSize = 3
  override val spawnBlocks = Array(
    Array(f, f, t),
    Array(t, t, t),
    Array(f, f, f)
  )

}

sealed trait TetrisPieceRotation

case object Up extends TetrisPieceRotation
case object Right extends TetrisPieceRotation
case object Down extends TetrisPieceRotation
case object Left extends TetrisPieceRotation

case class MovingTetrisPiece(upperLeftCoords: (Int, Int), t: TetrisPiece, r: TetrisPieceRotation)
