package mood.sprites.items

import mood.sprites.player.guns.Ammo
import org.phaser.loader.LoaderPlugin.AssetKey

sealed trait ItemKey

sealed trait AmmoItemKey extends ItemKey
case object Clip extends AmmoItemKey

object ItemKey {
  def apply(stringRepresentation: String): Option[ItemKey] = stringRepresentation match {
    case "clip" => Some(Clip)
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

