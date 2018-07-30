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

object TetrisBoard { 

  def bigBang(
    fill: Seq[TetrisBlock],
    remove: Seq[(Int, Int)] = Seq(),
    add: Map[(Int, Int), TetrisBlock] = Map()
  ): Task[TetrisBoard] = {
    val ob = Task(TetrisBoard())
    val fb = fill.zipWithIndex.foldLeft(ob) {
      case (b, (r, i)) =>
        b.flatMap(yb => (0 until 10).foldLeft(Task(yb))((xb, n) => xb.flatMap(_.set(n, i, Some(r)))))
    }
    val rb = remove.foldLeft(fb) {
      case (b, (x, y)) => b.flatMap(_.set(x, y, None))
    }
    val ab = add.toSeq.foldLeft(rb) {
      case (b, ((x, y), s)) => b.flatMap(_.set(x, y, Some(s)))
    }
    ab
  }

}
