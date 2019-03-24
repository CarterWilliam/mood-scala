package mood.config

import io.circe.Decoder
import io.circe.generic.semiauto._
import mood.sprites.enemies.Enemy

case class GameConfig(
  enemies: Map[String, Enemy.Config]
)

object GameConfig {
  import mood.animation.circe.codecs.AnimationCodecs._

  private implicit val enemyAudioDecoder: Decoder[Enemy.Audio] = deriveDecoder[Enemy.Audio]
  private implicit val enemyAnimationsDecoder: Decoder[Enemy.Animations] = deriveDecoder[Enemy.Animations]
  private implicit val enemyDecoder: Decoder[Enemy.Config] = deriveDecoder[Enemy.Config]

  implicit val decoder: Decoder[GameConfig] = deriveDecoder[GameConfig]
}