package tf.bug.puyix.tetris

import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.scalatest._
import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, Future }

class TetrisSRSSpec extends WordSpec {

  def qa[T](f: Future[T]): T = Await.result(f, Duration.Inf)

  "TetrisSRS" when {
    "setting up a T-Spin Triple" should {
      val board = TetrisBoard.bigBang(Seq.fill(5)(TetrisT), Seq(
        (8, 4), (9, 4),
        (7, 3), (8, 3), (9, 3),
        (7, 2),
        (7, 1), (8, 1),
        (7, 0)
      ))
      val b = qa(board.runAsync)
      "produce a set board" in {
        assertResult("""          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |########  
                       |#######   
                       |####### ##
                       |#######  #
                       |####### ##
                       |""".stripMargin)(printBoard(b))
      }
      "give a correct transformation on rotation" in {
        val piece = MovingTetrisPiece((7, 2), TetrisT, TetrisPieceRotation.Up)
        assertResult(Some(-1, -2))(qa(TetrisSRS.getValidTranslation(b, piece, TetrisPieceRotation.Right).runAsync))
      }
    }
    "setting up an Z-Spin Triple" should {
      val board = TetrisBoard.bigBang(Seq.fill(3)(TetrisZ), Seq(
        (6, 2),
        (5, 1), (6, 1),
        (5, 0)
      ), Map((4, 3) -> TetrisZ))
      val b = qa(board.runAsync)
      "produce a set board" in {
        assertResult("""          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |    #     
                       |###### ###
                       |#####  ###
                       |##### ####
                       |""".stripMargin)(printBoard(b))
      }
      "give a correct transformation on rotation" in {
        val piece = MovingTetrisPiece((4, 2), TetrisZ, TetrisPieceRotation.Up)
        assertResult(Some(0, -2))(qa(TetrisSRS.getValidTranslation(b, piece, TetrisPieceRotation.Right).runAsync))
      }
    }
    "setting up an S-Spin Triple" should {
      val board = TetrisBoard.bigBang(Seq.fill(3)(TetrisS), Seq(
        (3, 2),
        (3, 1), (4, 1),
        (4, 0)
      ), Map((5, 3) -> TetrisS))
      val b = qa(board.runAsync)
      "produce a set board" in {
        assertResult("""          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |          
                       |     #    
                       |### ######
                       |###  #####
                       |#### #####
                       |""".stripMargin)(printBoard(b))
      }
      "give a correct transformation on rotation" in {
        val piece = MovingTetrisPiece((3, 2), TetrisS, TetrisPieceRotation.Up)
        assertResult(Some(0, -2))(qa(TetrisSRS.getValidTranslation(b, piece, TetrisPieceRotation.Left).runAsync))
      }
    }
    "setting up a leftwards I-Spin Single" should {
      "produce a set board" in {

      }
    }
  }

  def printBoard(b: TetrisBoard): String = {
    (0 until 22).toSeq.reverse.foldLeft("")((s, y) => {
      s + (0 until 10).foldLeft("")((t, x) => {
        (b / (x, y)) match {
          case None => t + " "
          case Some(_) => t + "#"
        }
      }) + "\n"
    })
  }

  def printBoardWithPiece(b: TetrisBoard, p: MovingTetrisPiece): String = {
    val pl = p.positions
    (0 until 22).toSeq.reverse.foldLeft("")((s, y) => {
      s + (0 until 10).foldLeft("")((t, x) => {
        if(pl.contains((x, y))) {
          t + "x"
        } else {
          (b / (x, y)) match {
            case None => t + " "
            case Some(_) => t + "#"
          }
        }
      }) + "\n"
    })
  }
}
