package mood.input

import mood.input.PlayerInput._
import mood.spacial.Direction._

class PlayerInput(keyboard: MoodKeyboardInput) {
  private val directionVectorCache = new Cache[(Int, Int)]
  private val directionCache = new Cache[Option[CompassDirection]]
  private val weaponSwitchCache = new Cache[Option[WeaponSwitch]]

  def isFiring: Boolean = keyboard.space.isDown

  def isMoving: Boolean = {
    directionVector != (0,0) // Not an owl
  }

  def isStrafing: Boolean = keyboard.shift.isDown

  def direction: Option[CompassDirection] = directionCache.fetch {
    directionVector match {
      case (0, 0) => None
      case (0, 1) => Some(North)
      case (1, 1) => Some(NorthEast)
      case (1, 0) => Some(East)
      case (1, -1) => Some(SouthEast)
      case (0, -1) => Some(South)
      case (-1, -1) => Some(SouthWest)
      case (-1, 0) => Some(West)
      case (-1, 1) => Some(NorthWest)
    }
  }

  def weaponSwitch: Option[WeaponSwitch] = weaponSwitchCache.fetch {
    if (keyboard.pistol.isDown) Some(WeaponSwitchPistol)
    else if (keyboard.shotgun.isDown) Some(WeaponSwitchShotgun)
    else if (keyboard.chaingun.isDown) Some(WeaponSwitchChaingun)
//    else if (keyboard.rocketLauncher.isDown) Some(WeaponSwitchRocketLauncher)
//    else if (keyboard.plasmaRifle.isDown) Some(WeaponSwitchPlasmaRifle)
//    else if (keyboard.bfg9000.isDown) Some(WeaponSwitchBFG)
    else None
  }

  def invalidateCaches(): Unit = {
    directionVectorCache.invalidate()
    directionCache.invalidate()
    weaponSwitchCache.invalidate()
  }

  private def directionVector: (Int, Int) = directionVectorCache.fetch {
    var movingX = 0
    var movingY = 0

    if (keyboard.left.isDown) movingX -= 1
    if (keyboard.right.isDown) movingX += 1
    if (keyboard.up.isDown) movingY += 1
    if (keyboard.down.isDown) movingY -= 1

    (movingX, movingY)
  }

}

class Cache[T] {
  private var value: Option[T] = None

  def fetch(getValue: => T): T = value.getOrElse {
    val retrieved = getValue
    value = Some(retrieved)
    retrieved
  }

  def invalidate(): Unit =
    value = None
}

object PlayerInput {
  sealed trait WeaponSwitch
//  case object WeaponSwitchChainSaw extends WeaponSwitch
  case object WeaponSwitchPistol extends WeaponSwitch
  case object WeaponSwitchShotgun extends WeaponSwitch
  case object WeaponSwitchChaingun extends WeaponSwitch
//  case object WeaponSwitchRocketLauncher extends WeaponSwitch
//  case object WeaponSwitchPlasmaRifle extends WeaponSwitch
//  case object WeaponSwitchBFG extends WeaponSwitch
}
