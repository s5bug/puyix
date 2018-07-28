package tf.bug.puyix.tetris

case class TetrisBoard(blocks: Seq[Seq[Option[TetrisBlock]]] = Seq.fill(10, 22)(None)) {

  def /(x: Int, y: Int): Option[TetrisBlock] = blocks(x)(y)
  def /(c: (Int, Int)): Option[TetrisBlock] = /(c._1, c._2)

}
