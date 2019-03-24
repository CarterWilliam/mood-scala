package mood.util

object Direction {
  sealed trait Direction { def radians: Double }
  case object North extends Direction { val radians: Double = -Math.PI / 2 }
  case object NorthEast extends Direction { val radians: Double = -Math.PI / 4 }
  case object East extends Direction { val radians: Double = 0 }
  case object SouthEast extends Direction { val radians: Double = Math.PI / 4 }
  case object South extends Direction { val radians: Double = Math.PI / 2 }
  case object SouthWest extends Direction { val radians: Double = 3*Math.PI / 4 }
  case object West extends Direction { val radians: Double = Math.PI }
  case object NorthWest extends Direction { val radians: Double = -3*Math.PI / 4 }

  val PiOver8: Double = Math.PI / 8
  val TwoPi: Double = Math.PI * 2
}
