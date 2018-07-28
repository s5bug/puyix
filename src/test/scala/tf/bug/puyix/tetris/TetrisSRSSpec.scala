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
      "produce a set board" in {
        val board = TetrisBoard()
        val filledRows = (0 until 5).foldLeft(Task(board))((yb, y) => {
          (0 until 10).foldLeft(yb)((xb, x) => {
            xb.flatMap(f => f.set(x, y, Some(TetrisL)))
          })
        })
          .flatMap(f => f.set(8, 4, None))
          .flatMap(f => f.set(9, 4, None))
          .flatMap(f => f.set(7, 3, None))
          .flatMap(f => f.set(8, 3, None))
          .flatMap(f => f.set(9, 3, None))
          .flatMap(f => f.set(7, 2, None))
          .flatMap(f => f.set(7, 1, None))
          .flatMap(f => f.set(8, 1, None))
          .flatMap(f => f.set(7, 0, None))
        val b = qa(filledRows.runAsync)
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
        val board = TetrisBoard()
        val filledRows = (0 until 5).foldLeft(Task(board))((yb, y) => {
          (0 until 10).foldLeft(yb)((xb, x) => {
            xb.flatMap(f => f.set(x, y, Some(TetrisL)))
          })
        })
          .flatMap(f => f.set(8, 4, None))
          .flatMap(f => f.set(9, 4, None))
          .flatMap(f => f.set(7, 3, None))
          .flatMap(f => f.set(8, 3, None))
          .flatMap(f => f.set(9, 3, None))
          .flatMap(f => f.set(7, 2, None))
          .flatMap(f => f.set(7, 1, None))
          .flatMap(f => f.set(8, 1, None))
          .flatMap(f => f.set(7, 0, None))
        val b = qa(filledRows.runAsync)
        val piece = MovingTetrisPiece((7, 2), TetrisT, TetrisPieceRotation.Up)
        println(printBoardWithPiece(b, piece))
        println(printBoardWithPiece(b, piece.copy(rotation = TetrisPieceRotation.Right)))
        assertResult(Some(-1, -2))(qa(TetrisSRS.getValidTranslation(b, piece, TetrisPieceRotation.Right).runAsync))
      }
    }
  }

  def printBoard(b: TetrisBoard): String = {
    var str = ""
    (0 until 22).toSeq.reverse.foreach(y => {
      (0 until 10).foreach(x => {
        (b / (x, y)) match {
          case None => str += " "
          case Some(_) => str += "#"
        }
      })
      str += "\n"
    })
    str
  }

  def printBoardWithPiece(b: TetrisBoard, p: MovingTetrisPiece): String = {
    val pl = p.positions
    var str = ""
    (0 until 22).toSeq.reverse.foreach(y => {
      (0 until 10).foreach(x => {
        if(pl.contains((x, y))) {
          str += "x"
        } else {
          (b / (x, y)) match {
            case None => str += " "
            case Some(_) => str += "#"
          }
        }
      })
      str += "\n"
    })
    str
  }

}
