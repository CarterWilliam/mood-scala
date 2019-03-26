package mood.sprites.player.guns

case class WeaponConfig(
  ammoType: Ammo,
  ammoCost: Int,
  fireInterval: Int,
  maxMissRadians: Double
)

sealed trait WeaponKey
case object Pistol extends WeaponKey

object WeaponKey {
  def apply(key: String): Option[WeaponKey] = key match {
    case "pistol" => Some(Pistol)
    case _ => None
  }
}
