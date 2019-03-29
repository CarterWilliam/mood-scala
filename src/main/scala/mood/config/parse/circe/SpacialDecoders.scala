package mood.config.parse.circe

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import mood.spacial.Direction.CompassDirection
import mood.spacial.Position.Coordinates

object SpacialDecoders {

  implicit val decodeDirection: Decoder[CompassDirection] = {
    Decoder.decodeString.emap[CompassDirection] { key =>
      CompassDirection(key) match {
        case Some(action) => Right(action)
        case None => Left(s"Invalid direction '$key'")
      }
    }
  }

  implicit val decodeCoordinates: Decoder[Coordinates] = deriveDecoder[Coordinates]

}
