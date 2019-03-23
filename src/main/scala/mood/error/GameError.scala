package mood.error

import mood.Log
import org.phaser.Game

case class GameError(message: String, cause: Option[Throwable])
  extends Throwable(message, cause.orNull)

object GameError {

  def apply(message: String, cause: Throwable): GameError = GameError(message, Some(cause))

  def fatal(game: Game, error: GameError): Nothing = {
    Log.error(error.getMessage)
    game.destroy(removeCanvas = false, noReturn = true)
    throw error
  }
}
