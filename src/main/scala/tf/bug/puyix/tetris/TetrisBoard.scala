package tf.bug.puyix.tetris

import monix.eval.Task

case class TetrisBoard(blocks: Seq[Seq[Option[TetrisBlock]]] = Seq.fill(10, 22)(None)) {

  def /(x: Int, y: Int): Option[TetrisBlock] = blocks(x)(y)
  def /(c: (Int, Int)): Option[TetrisBlock] = c match {
    case (x, y) => /(x, y)
  }

  def set(x: Int, y: Int, b: Option[TetrisBlock]): Task[TetrisBoard] = Task {
    val newBlocks = blocks.zipWithIndex.map { 
      case (col, tx) =>
        col.zipWithIndex.map {
          case(cell, ty) =>
            if((tx, ty) == (x, y)) {
              b
            } else {
              cell
            }
        }
    }
    TetrisBoard(newBlocks)
  }
  def remove(y: Int): Task[TetrisBoard] = Task {
    (0 to 10).foldLeft(Task.pure(this))((board, x) => board.flatMap(rb => rb set (x, y, None)))
  }.flatten

}
