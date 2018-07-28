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

  /**
    * See [[http://tetris.wikia.com/wiki/SRS SRS documetation]] */
  def getSRSTranslations(p: TetrisPiece, ir: TetrisPieceRotation, or: TetrisPieceRotation): Seq[(Int, Int)] = {
    import TetrisPieceRotation._
    if(p == TetrisI) {
      (ir, or) match {
        case (Up, Right) => Seq((0, 0), (-2, 0), (1, 0), (-2, -1), (1, 2))
        case (Right, Up) => Seq((0, 0), (2, 0), (-1, 0), (2, 1), (-1, -2))
        case (Right, Down) => Seq((0, 0), (-1, 0), (2, 0), (-1, 2), (2, -1))
        case (Down, Right) => Seq((0, 0), (1, 0), (-2, 0), (1, -2), (-2, 1))
        case (Down, Left) => Seq((0, 0), (2, 0), (-1, 0), (2, 1), (-1, -2))
        case (Left, Down) => Seq((0, 0), (-2, 0), (1, 0), (-2, -1), (1, 2))
        case (Left, Up) => Seq((0, 0), (1, 0), (-2, 0), (1, -2), (-2, 1))
        case (Up, Left) => Seq((0, 0), (-1, 0), (2, 0), (-1, 2), (2, -1))
      }
    } else {
      (ir, or) match {
        case (Up, Right) => Seq((0, 0), (-1, 0), (-1, 1), (0, -2), (-1, -2))
        case (Right, Up) => Seq((0, 0), (1, 0), (1,-1), (0, 2), (1, 2))
        case (Right, Down) => Seq((0, 0), (1, 0), (1, -1), (0, 2), (1, 2))
        case (Down, Right) => Seq((0, 0), (-1, 0), (-1, 1), (0, -2), (-1, -2))
        case (Down, Left) => Seq((0, 0), (1, 0), (1, 1), (0, -2), (1, -2))
        case (Left, Down) => Seq((0, 0), (-1, 0), (-1, -1), (0, 2), (-1, 2))
        case (Left, Up) => Seq((0, 0), (-1, 0), (-1, -1), (0, 2), (-1, 2))
        case (Up, Left) => Seq((0, 0), (1, 0), (1, 1), (0, -2), (1,-2))
      }
    }
  }

}
