package mood.config.parse.circe

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import mood.sprites.player.Player
import mood.util.Direction.CompassDirection

object StateDecoders {
  import AmmoDecoders._

  implicit val decodeAction: Decoder[Player.Action] = {
    Decoder.decodeString.emap[Player.Action] { key =>
      Player.Action(key) match {
        case Some(action) => Right(action)
        case None => Left(s"Invalid action '$key'")
      }
    }
  }

  implicit val decodeDirection: Decoder[CompassDirection] = {
    Decoder.decodeString.emap[CompassDirection] { key =>
      CompassDirection(key) match {
        case Some(action) => Right(action)
        case None => Left(s"Invalid direction '$key'")
      }
    }
  }

  implicit val stateDecoder: Decoder[Player.State] = deriveDecoder[Player.State]

}
