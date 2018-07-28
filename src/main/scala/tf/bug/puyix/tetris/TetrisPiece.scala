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
object TetrisPieceRotation {

  case object Up extends TetrisPieceRotation
  case object Right extends TetrisPieceRotation
  case object Down extends TetrisPieceRotation
  case object Left extends TetrisPieceRotation

}

case class MovingTetrisPiece(lowerLeftCoords: (Int, Int), piece: TetrisPiece, rotation: TetrisPieceRotation) {

  /**
    * Turns the blockmask specified by the piece into a relative (x)(y) mask of its currest location.
    * Lower y coordinates mean closer to the bottom of the board. */
  def getBlocks: Array[Array[Boolean]] = {
    import TetrisPieceRotation._
    val s = piece.spawnBlocks
    rotation match {
      case Up => s.reverse.transpose
      case Right => s.reverse
      case Down => s.transpose
      case Left => s
    }
  }

  def positions: Array[(Int, Int)] = {
    val (cx, cy) = lowerLeftCoords
    getBlocks.zipWithIndex.flatMap {
      case (col, x) => col.zipWithIndex.map {
        case (set, y) => ((x + cx, y + cy), set)
      }
    }.filter {
      case (coords, set) => set
    }.map {
      case (coords, set) => coords
    }
  }

}
