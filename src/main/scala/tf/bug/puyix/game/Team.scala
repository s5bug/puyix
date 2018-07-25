package tf.bug.puyix.game

import tf.bug.puyix.TeamedPlayer
import tf.bug.puyix.game.board.{Player, TeamedPlayer}

class Team(private val ps: Traversable[Player[_]]) {

  val players: Traversable[TeamedPlayer[_]] = ps.map(_(this))

}
