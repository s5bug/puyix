package tf.bug.puyix.tetris

import monix.eval.Task
import scala.concurrent.Await
import scala.concurrent.duration.Duration

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
    * @returns `Some(translation)` if piece can be rotated that way, otherwise `None` */
  def getValidTranslation(
    board: TetrisBoard,
    piece: MovingTetrisPiece,
    or: TetrisPieceRotation
  ): Task[Option[(Int, Int)]] = Task {
    val translations = getSRSTranslations(piece.piece, piece.rotation, or)
    val rotatedPiece = piece.copy(rotation = or)
    val (rx, ry) = rotatedPiece.lowerLeftCoords
    translations.dropWhile { 
      case (x, y) =>
        val translatedPiece = rotatedPiece.copy(lowerLeftCoords = (x + rx, y + ry))
        import monix.execution.Scheduler.Implicits.global
        val f = isTetrisPieceValid(board, translatedPiece).runAsync
        val result = Await.result(f, Duration.Inf)
        !result
    }.headOption
  }

  def isTetrisPieceValid(board: TetrisBoard, piece: MovingTetrisPiece): Task[Boolean] = Task {
    val setCoords = piece.positions
    setCoords.forall(t => !((board / t).isDefined))
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
        case _ => throw new IllegalArgumentException("Invalid rotation points specified")
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
        case _ => throw new IllegalArgumentException("Invalid rotation points specified")
      }
    }
  }

}
