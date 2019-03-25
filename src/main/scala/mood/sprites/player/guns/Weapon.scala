package mood.sprites.player.guns

trait Weapon {
  def ammoType: Ammo
  def ammoCost: Int
}

case object Pistol extends Weapon {
  val ammoType: Ammo = Bullets
  def ammoCost: Int = 1
}
