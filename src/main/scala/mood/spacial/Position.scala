package mood.util

object Position {

  case class Size(width: Int, height: Int)

  case class Coordinates(x: Double, y: Double) {
    def + (offset: Offset): Coordinates = copy(x = x + offset.x, y = y + offset.y)
  }

  case class Offset(x: Int, y: Int)

}
