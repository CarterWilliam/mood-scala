package mood.config.parse.circe

import io.circe.Decoder
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto.deriveDecoder
import mood.sprites.player.Player
import mood.spacial.Direction.CompassDirection

object StateDecoders {
  import WithDefaultsConfig._
  import AmmoDecoders._
  import SpacialDecoders._
  import WeaponDecoders._

  implicit val decodeAction: Decoder[Player.Action] = {
    Decoder.decodeString.emap[Player.Action] { key =>
      Player.Action(key) match {
        case Some(action) => Right(action)
        case None => Left(s"Invalid action '$key'")
      }
    }
  }

  implicit val stateDecoder: Decoder[Player.State] = deriveDecoder[Player.State]

}
