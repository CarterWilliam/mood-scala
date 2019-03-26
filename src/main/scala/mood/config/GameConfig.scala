package mood.config

import io.circe.Decoder
import io.circe.generic.semiauto._
import mood.sprites.enemies.Enemy
import mood.sprites.items.{AmmoItemConfig, AmmoItemKey, ItemConfig, ItemKey}
import mood.sprites.player.Player

case class GameConfig(
  player: Player.Config,
  enemies: Map[String, Enemy.Config],
  items: ItemsConfig
)

case class ItemsConfig(ammo: Map[ItemKey, AmmoItemConfig]) {
  def apply(itemKey: ItemKey): ItemConfig = itemKey match {
    case ammoKey: AmmoItemKey => ammo(ammoKey)
  }
}

object GameConfig {
  import mood.config.parse.circe.AnimationDecoders._
  import mood.config.parse.circe.ItemDecoders._
  import mood.config.parse.circe.AmmoDecoders.decodeAmmo
  import mood.config.parse.circe.StateDecoders._

  private implicit val sizeDecoder: Decoder[Player.Size] = deriveDecoder[Player.Size]
  private implicit val offsetDecoder: Decoder[Player.Offset] = deriveDecoder[Player.Offset]
  private implicit val playerAudioDecoder: Decoder[Player.Audio] = deriveDecoder[Player.Audio]
  private implicit val playerAnimationsDecoder: Decoder[Player.Animations] = deriveDecoder[Player.Animations]
  private implicit val playerConfigDecoder: Decoder[Player.Config] = deriveDecoder[Player.Config]

  private implicit val enemyAudioDecoder: Decoder[Enemy.Audio] = deriveDecoder[Enemy.Audio]
  private implicit val enemyAnimationsDecoder: Decoder[Enemy.Animations] = deriveDecoder[Enemy.Animations]
  private implicit val enemyDecoder: Decoder[Enemy.Config] = deriveDecoder[Enemy.Config]

  private implicit val ammoItemDecoder: Decoder[AmmoItemConfig] = deriveDecoder[AmmoItemConfig]
  private implicit val itemConfigDecoder: Decoder[ItemsConfig] = deriveDecoder[ItemsConfig]

  implicit val decoder: Decoder[GameConfig] = deriveDecoder[GameConfig]
}