package mood.sprites.player.guns

trait Weapon {
  def ammoType: Ammo
  def ammoCost: Int
}

object Weapon {
  def apply(key: String): Option[Weapon] = key match {
    case "pistol" => Some(Pistol)
    case _ => None
  }
}
case object Pistol extends Weapon {
  val ammoType: Ammo = Bullets
  def ammoCost: Int = 1
}
