package tf.bug.puyix.game.board

import tf.bug.puyix.game.Team

class Player[A](character: PuyixCharacter, board: A)(implicit mode: GameMode[A]) {

  def apply(team: Team): TeamedPlayer[A] = new TeamedPlayer[A](team, character, board)(mode)

}

class TeamedPlayer[A](team: Team, character: PuyixCharacter, board: A)(implicit mode: GameMode[A])
