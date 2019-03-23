package mood.config

import io.circe.Decoder
import io.circe.generic.semiauto._
import mood.sprites.enemies.Enemy

case class GameConfig(
  enemies: Map[String, Enemy.Config]
)

object GameConfig {
  implicit private val enemyAudioDecoder: Decoder[Enemy.Audio] = deriveDecoder[Enemy.Audio]
  implicit private val enemyAnimationsDecoder: Decoder[Enemy.Animations] = deriveDecoder[Enemy.Animations]
  implicit private val enemyDecoder: Decoder[Enemy.Config] = deriveDecoder[Enemy.Config]

  implicit val decoder: Decoder[GameConfig] = deriveDecoder[GameConfig]
}