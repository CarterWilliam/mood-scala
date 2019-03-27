package mood.sprites.player.guns

import mood.sprites.projectiles.Projectile
import org.phaser.loader.LoaderPlugin.AssetKey

case class WeaponConfig(
  ammoType: Ammo,
  ammoCost: Int,
  fireInterval: Int,
  fireAudio: AssetKey,
  maxMissRadians: Double,
  burst: Int = 1,
  projectile: Projectile.Config
)

sealed trait WeaponKey
case object Pistol extends WeaponKey
case object Shotgun extends WeaponKey

object WeaponKey {
  def apply(key: String): Option[WeaponKey] = key match {
    case "pistol" => Some(Pistol)
    case "shotgun" => Some(Shotgun)
    case _ => None
  }
}
