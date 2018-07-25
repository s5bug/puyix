package tf.bug.puyix.game.board

import tf.bug.puyix.game.Team

class Player[A <: GameMode](character: PuyixCharacter, board: A) {

  def apply(team: Team): TeamedPlayer[A] = new TeamedPlayer[A](team, character, board)

}

object Player {

  def apply[A <: GameMode](character: PuyixCharacter, board: A): Player[A] = new Player(character, board)
  def apply[A: GameModeLike](character: PuyixCharacter, board: A)(implicit gameModeLike: GameModeLike[A]): Player[GameMode] = {
    val gm = gameModeLike.asGameMode(board)
    Player(character, gm)
  }

}

class TeamedPlayer[A <: GameMode](team: Team, character: PuyixCharacter, board: A)
