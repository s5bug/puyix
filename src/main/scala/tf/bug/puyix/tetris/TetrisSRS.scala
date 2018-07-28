package tf.bug.puyix.tetris

object TetrisSRS {

  def processMoves(
    board: TetrisBoard,
    piece: MovingTetrisPiece,
    moves: Seq[TetrisMove]
  ): (TetrisBoard, Option[MovingTetrisPiece]) = {
    (board, Some(piece)) // TODO
  }

}
