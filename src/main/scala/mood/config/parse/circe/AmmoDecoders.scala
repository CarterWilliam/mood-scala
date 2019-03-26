package mood.config.parse.circe

import io.circe.Decoder
import mood.sprites.player.guns.Ammo

object AmmoDecoders {

  implicit val decodeAmmo: Decoder[Ammo] = {
    Decoder.decodeString.emap[Ammo] { key =>
      Ammo.apply(key) match {
        case Some(ammo) => Right(ammo)
        case None => Left(s"Invalid ammo key '$key'")
      }
    }
  }

}
