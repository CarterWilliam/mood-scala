package mood.input

import mood.input.PlayerInput._
import mood.spacial.Direction._

class PlayerInput(keyboard: MoodKeyboardInput) {
  var cache: Map[String, Any] = Map.empty

  def isFiring: Boolean = keyboard.space.isDown

  def isMoving: Boolean = {
    directionVector != (0,0) // Not an owl
  }

  def isStrafing: Boolean = keyboard.shift.isDown

  def direction: Option[CompassDirection] = cached("direction") {
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

  def weaponSwitch: Option[WeaponSwitch] = cached("weapon-switch") {
    if (keyboard.pistol.isDown) Some(Pistol)
    else if (keyboard.shotgun.isDown) Some(Shotgun)
    else if (keyboard.chaingun.isDown) Some(Chaingun)
    else if (keyboard.rocketLauncher.isDown) Some(RocketLauncher)
    else if (keyboard.plasmaRifle.isDown) Some(PlasmaRifle)
    else if (keyboard.bfg9000.isDown) Some(BFG)
    else None
  }

  def invalidateCaches(): Unit = {
    cache = Map.empty
  }

  private def directionVector: (Int, Int) = cached("direction-vector") {
    var movingX = 0
    var movingY = 0

    if (keyboard.left.isDown) movingX -= 1
    if (keyboard.right.isDown) movingX += 1
    if (keyboard.up.isDown) movingY += 1
    if (keyboard.down.isDown) movingY -= 1

    (movingX, movingY)
  }

  private def cached[Object](key: String)(calculate: => Object): Object = {
    val calculated = calculate
    cache += key -> calculated
    calculated
  }
}

object PlayerInput {
  sealed trait WeaponSwitch
  case object ChainSaw extends WeaponSwitch
  case object Pistol extends WeaponSwitch
  case object Shotgun extends WeaponSwitch
  case object Chaingun extends WeaponSwitch
  case object RocketLauncher extends WeaponSwitch
  case object PlasmaRifle extends WeaponSwitch
  case object BFG extends WeaponSwitch
}
