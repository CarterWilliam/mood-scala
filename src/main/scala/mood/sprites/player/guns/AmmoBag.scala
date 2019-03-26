package mood.sprites.player.guns

import AmmoBag._

case class AmmoBag(
  bullets: AmmoPocket = AmmoPocket(50, 200),
  shells: AmmoPocket = AmmoPocket(0, 50),
  rockets: AmmoPocket = AmmoPocket(0, 50),
  plasma: AmmoPocket = AmmoPocket(0, 200)
) {

  private def pocket(ammoType: Ammo): AmmoPocket = ammoType match {
    case Bullets => bullets
    case Shells => shells
    case Rockets => rockets
    case Plasma => plasma
  }

  def remaining(ammoType: Ammo): Int = pocket(ammoType).remaining

  def has(ammoType: Ammo, amount: Int): Boolean = pocket(ammoType).remaining >= amount

  def add(ammoType: Ammo, amount: Int): AmmoBag = {
    modify(ammoType, amount)(this)
  }

  def take(`type`: Ammo, amount: Int): Option[AmmoBag] = {
    if (has(`type`, amount)) Some(modify(`type`, -amount)(this)) else None
  }
}

object AmmoBag {
  import monocle.Lens
  import monocle.macros.GenLens

  private val bullets: Lens[AmmoBag, AmmoPocket] = GenLens[AmmoBag](_.bullets)
  private val shells: Lens[AmmoBag, AmmoPocket] = GenLens[AmmoBag](_.shells)
  private val rockets: Lens[AmmoBag, AmmoPocket] = GenLens[AmmoBag](_.rockets)
  private val plasma: Lens[AmmoBag, AmmoPocket] = GenLens[AmmoBag](_.plasma)

  private val remaining: Lens[AmmoPocket, Int] = GenLens[AmmoPocket](_.remaining)
  private val maximum: Lens[AmmoPocket, Int] = GenLens[AmmoPocket](_.maximum)

  private def modify(ammoType: Ammo): Lens[AmmoBag, AmmoPocket] = ammoType match {
    case Bullets => bullets
    case Shells => shells
    case Rockets => rockets
    case Plasma => plasma
  }

  def modify(ammoType: Ammo, amount: Int)(bag: AmmoBag): AmmoBag = {
    val max = (modify(ammoType) composeLens maximum).get(bag)
    (modify(ammoType) ^|-> remaining).modify { current =>
      Math.min(current + amount, max)
    }(bag)
  }

}

case class AmmoPocket(remaining: Int, maximum: Int)

sealed trait Ammo
object Ammo {
  def apply(key: String): Option[Ammo] = key match {
    case "bullets" => Some(Bullets)
    case "shells" => Some(Shells)
    case "rockets" => Some(Rockets)
    case "plasma" => Some(Plasma)
    case _ => None
  }
}
case object Bullets extends Ammo
case object Shells extends Ammo
case object Rockets extends Ammo
case object Plasma extends Ammo
