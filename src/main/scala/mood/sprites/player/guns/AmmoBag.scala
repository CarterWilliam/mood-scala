package mood.sprites.player.guns

case class AmmoBag(
  bullets: AmmoPocket = AmmoPocket(50, 200),
  shells: AmmoPocket = AmmoPocket(0, 50),
  rockets: AmmoPocket = AmmoPocket(0, 50),
  plasma: AmmoPocket = AmmoPocket(0, 200)
) {

  def apply(`type`: Ammo): AmmoPocket = `type` match {
    case Bullets => bullets
    case Shells => shells
    case Rockets => rockets
    case Plasma => plasma
  }

  def remaining(`type`: Ammo): Int = apply(`type`).remaining

  def has(`type`: Ammo, amount: Int): Boolean = apply(`type`).remaining >= amount

  def take(`type`: Ammo, amount: Int): Option[AmmoBag] = `type` match {
    case Bullets if has(Bullets, amount) =>
      Some(copy(bullets = bullets.copy(remaining = bullets.remaining - amount)))
    case Shells if has(Shells, amount) =>
      Some(copy(shells = shells.copy(remaining = shells.remaining - amount)))
    case Rockets if has(Rockets, amount) =>
      Some(copy(rockets = rockets.copy(remaining = rockets.remaining - amount)))
    case Plasma if has(Plasma, amount) =>
      Some(copy(plasma = plasma.copy(remaining = plasma.remaining - amount)))
    case _ =>
      None
  }
}

case class AmmoPocket(remaining: Int, maximum: Int)

sealed trait Ammo
case object Bullets extends Ammo
case object Shells extends Ammo
case object Rockets extends Ammo
case object Plasma extends Ammo
