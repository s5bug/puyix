package tf.bug.puyix.tetris

sealed trait TetrisMove

case object TetrisGravity
case object TetrisSoftDrop
case object TetrisHardDrop
case object TetrisLeft
case object TetrisRight
case object TetrisClockwise
case object TetrisCounterClockwise
