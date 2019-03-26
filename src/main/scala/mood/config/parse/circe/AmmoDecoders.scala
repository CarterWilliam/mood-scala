package mood.config.parse.circe

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import mood.sprites.player.guns.{Ammo, AmmoBag, AmmoPocket}

object AmmoDecoders {

  implicit val decodeAmmo: Decoder[Ammo] = {
    Decoder.decodeString.emap[Ammo] { key =>
      Ammo(key) match {
        case Some(ammo) => Right(ammo)
        case None => Left(s"Invalid ammo key '$key'")
      }
    }
  }

  implicit val ammoPocketDecoder: Decoder[AmmoPocket] = deriveDecoder[AmmoPocket]
  implicit val ammoBagDecoder: Decoder[AmmoBag] = deriveDecoder[AmmoBag]
}
