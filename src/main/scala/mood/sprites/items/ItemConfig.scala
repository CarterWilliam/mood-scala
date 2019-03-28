package mood.sprites.items

import mood.sprites.player.guns.{Ammo, WeaponKey}
import org.phaser.loader.LoaderPlugin.AssetKey

sealed trait ItemKey

sealed trait AmmoItemKey extends ItemKey
case object ClipItem extends AmmoItemKey

sealed trait WeaponItemKey extends ItemKey
case object ShotgunItem extends WeaponItemKey
case object ChaingunItem extends WeaponItemKey

object ItemKey {
  def apply(stringRepresentation: String): Option[ItemKey] = stringRepresentation match {
    case "clip" => Some(ClipItem)
    case "shotgun" => Some(ShotgunItem)
    case "chaingun" => Some(ChaingunItem)
    case _ => None
  }
}


sealed trait ItemConfig {
  def image: AssetKey
  def pickupAudio: AssetKey
}

case class AmmoItemConfig(
  image: AssetKey,
  pickupAudio: AssetKey,
  ammoType: Ammo,
  amount: Int) extends ItemConfig

case class WeaponItemConfig(
  image: AssetKey,
  pickupAudio: AssetKey,
  weapon: WeaponKey,
  ammoType: Ammo,
  ammoAmount: Int) extends ItemConfig
