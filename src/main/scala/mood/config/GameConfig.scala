package mood.config

import io.circe.Decoder
import io.circe.generic.semiauto._
import mood.sprites.Player
import mood.sprites.enemies.Enemy

case class GameConfig(
  player: Player.Config,
  enemies: Map[String, Enemy.Config]
)

object GameConfig {
  import mood.animation.circe.codecs.AnimationCodecs._

  private implicit val sizeDecoder: Decoder[Player.Size] = deriveDecoder[Player.Size]
  private implicit val offsetDecoder: Decoder[Player.Offset] = deriveDecoder[Player.Offset]
  private implicit val playerAudioDecoder: Decoder[Player.Audio] = deriveDecoder[Player.Audio]
  private implicit val playerAnimationsDecoder: Decoder[Player.Animations] = deriveDecoder[Player.Animations]
  private implicit val playerConfigDecoder: Decoder[Player.Config] = deriveDecoder[Player.Config]

  private implicit val enemyAudioDecoder: Decoder[Enemy.Audio] = deriveDecoder[Enemy.Audio]
  private implicit val enemyAnimationsDecoder: Decoder[Enemy.Animations] = deriveDecoder[Enemy.Animations]
  private implicit val enemyDecoder: Decoder[Enemy.Config] = deriveDecoder[Enemy.Config]

  implicit val decoder: Decoder[GameConfig] = deriveDecoder[GameConfig]
}