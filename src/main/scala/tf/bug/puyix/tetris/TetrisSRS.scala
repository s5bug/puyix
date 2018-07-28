package tf.bug.puyix.tetris

import monix.eval.Task

object TetrisSRS {

  type MoveState = Task[(TetrisBoard, Option[MovingTetrisPiece])]

  def processMoves(
    board: TetrisBoard,
    piece: MovingTetrisPiece,
    moves: Seq[TetrisMove]
  ): Task[(TetrisBoard, Option[MovingTetrisPiece])] = Task {
    moves.foldLeft[MoveState](Task((board, Some(piece))))((task: MoveState, nmove: TetrisMove) => {
      task.flatMap {
        case (nboard, npieceOption) => npieceOption match {
          case Some(npiece) => processMove(nboard, npiece, nmove)
          case None => Task((nboard, None))
        }
      }
    })
  }.flatten

  def processMove(
    board: TetrisBoard,
    piece: MovingTetrisPiece,
    move: TetrisMove
  ): Task[(TetrisBoard, Option[MovingTetrisPiece])] = Task {
    (board, Some(piece))
  }

}
