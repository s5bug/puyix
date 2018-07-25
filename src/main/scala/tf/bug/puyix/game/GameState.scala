package tf.bug.puyix.game

import tf.bug.puyix.game.board.Player

class GameState(private val players: Traversable[Traversable[Player[_]]]) {

  val teams: Traversable[Team] = players.map(ps => new Team(ps))

}
