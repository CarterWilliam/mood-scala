package mood.util

sealed trait Direction { def radians: Double }

case class ExplicitDirection(radians: Double) extends Direction

object Direction {
  sealed trait CompassDirection extends Direction
  case object North extends CompassDirection { val radians: Double = -Math.PI / 2 }
  case object NorthEast extends CompassDirection { val radians: Double = -Math.PI / 4 }
  case object East extends CompassDirection { val radians: Double = 0 }
  case object SouthEast extends CompassDirection { val radians: Double = Math.PI / 4 }
  case object South extends CompassDirection { val radians: Double = Math.PI / 2 }
  case object SouthWest extends CompassDirection { val radians: Double = 3*Math.PI / 4 }
  case object West extends CompassDirection { val radians: Double = Math.PI }
  case object NorthWest extends CompassDirection { val radians: Double = -3*Math.PI / 4 }

  object CompassDirection {
    def apply(key: String): Option[CompassDirection] = key match {
      case "north" => Some(North)
      case "northeast" => Some(NorthEast)
      case "east" => Some(East)
      case "southeast" => Some(SouthEast)
      case "south" => Some(South)
      case "southwest" => Some(SouthWest)
      case "west" => Some(West)
      case "northwest" => Some(NorthWest)
    }
  }

  val PiOver8: Double = Math.PI / 8
  val TwoPi: Double = Math.PI * 2
}
