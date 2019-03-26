package mood.sprites.player.guns

import AmmoBag._

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
      Some(removeBullets(amount)(this))
    case Shells if has(Shells, amount) =>
      Some(removeShells(amount)(this))
    case Rockets if has(Rockets, amount) =>
      Some(removeRockets(amount)(this))
    case Plasma if has(Plasma, amount) =>
      Some(removePlasma(amount)(this))
    case _ =>
      None
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

  def removeBullets(amount: Int)(bag: AmmoBag): AmmoBag =
    (bullets ^|-> remaining).modify(_ - amount)(bag)
  def removeShells(amount: Int)(bag: AmmoBag): AmmoBag =
    (shells ^|-> remaining).modify(_ - amount)(bag)
  def removeRockets(amount: Int)(bag: AmmoBag): AmmoBag =
    (rockets ^|-> remaining).modify(_ - amount)(bag)
  def removePlasma(amount: Int)(bag: AmmoBag): AmmoBag =
    (plasma ^|-> remaining).modify(_ - amount)(bag)
}

case class AmmoPocket(remaining: Int, maximum: Int)

sealed trait Ammo
case object Bullets extends Ammo
case object Shells extends Ammo
case object Rockets extends Ammo
case object Plasma extends Ammo
