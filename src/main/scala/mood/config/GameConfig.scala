package mood.config

import io.circe.Decoder
import io.circe.generic.semiauto._
import mood.sprites.enemies.Enemy
import mood.sprites.items._
import mood.sprites.player.Player
import mood.sprites.player.guns.{WeaponConfig, WeaponKey}
import mood.spacial.Position.{Offset, Size}
import org.phaser.loader.LoaderPlugin.AssetKey

case class GameConfig(
  player: Player.Config,
  weapons: Map[WeaponKey, WeaponConfig],
  enemies: Map[String, Enemy.Config],
  items: ItemsConfig,
  blood: BloodConfig
)

case class ItemsConfig(
  ammo: Map[ItemKey, AmmoItemConfig],
  weapons: Map[ItemKey, WeaponItemConfig]) {
  def apply(itemKey: ItemKey): ItemConfig = itemKey match {
    case ammoKey: AmmoItemKey => ammo(ammoKey)
    case weaponKey: WeaponItemKey => weapons(weaponKey)
  }
}

case class BloodConfig(particles: Int, lifespan: Int, gravity: Int, speed: Int)

object GameConfig {
  val LoadKey: AssetKey = "game-config"

  import mood.config.parse.circe.AmmoDecoders.decodeAmmo
  import mood.config.parse.circe.AnimationDecoders._
  import mood.config.parse.circe.ItemDecoders._
  import mood.config.parse.circe.StateDecoders._
  import mood.config.parse.circe.WeaponDecoders._

  private implicit val sizeDecoder: Decoder[Size] = deriveDecoder[Size]
  private implicit val offsetDecoder: Decoder[Offset] = deriveDecoder[Offset]

  private implicit val playerAudioDecoder: Decoder[Player.Audio] = deriveDecoder[Player.Audio]
  private implicit val playerAnimationsDecoder: Decoder[Player.Animations] = deriveDecoder[Player.Animations]
  private implicit val playerConfigDecoder: Decoder[Player.Config] = deriveDecoder[Player.Config]

  private implicit val itemDropDecoder: Decoder[Enemy.ItemDrop] = deriveDecoder[Enemy.ItemDrop]
  private implicit val enemyAudioDecoder: Decoder[Enemy.Audio] = deriveDecoder[Enemy.Audio]
  private implicit val enemyAnimationsDecoder: Decoder[Enemy.Animations] = deriveDecoder[Enemy.Animations]
  private implicit val enemyDecoder: Decoder[Enemy.Config] = deriveDecoder[Enemy.Config]

  private implicit val ammoItemDecoder: Decoder[AmmoItemConfig] = deriveDecoder[AmmoItemConfig]
  private implicit val weaponItemDecoder: Decoder[WeaponItemConfig] = deriveDecoder[WeaponItemConfig]
  private implicit val itemConfigDecoder: Decoder[ItemsConfig] = deriveDecoder[ItemsConfig]

  private implicit val bloodConfigDecoder: Decoder[BloodConfig] = deriveDecoder[BloodConfig]

  implicit val decoder: Decoder[GameConfig] = deriveDecoder[GameConfig]
}