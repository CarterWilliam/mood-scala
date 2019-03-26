package mood.config.parse.circe

import io.circe.{Decoder, KeyDecoder}
import io.circe.generic.semiauto.deriveDecoder
import mood.sprites.player.guns.{WeaponConfig, WeaponKey}

object WeaponDecoders {
  import AmmoDecoders._

  implicit val decodeWeaponKeyAsKey: KeyDecoder[WeaponKey] = new KeyDecoder[WeaponKey] {
    override def apply(key: String): Option[WeaponKey] = WeaponKey(key)
  }

  implicit val decodeWeaponKey: Decoder[WeaponKey] = {
    Decoder.decodeString.emap[WeaponKey] { key =>
      WeaponKey(key) match {
        case Some(weaponKey) => Right(weaponKey)
        case None => Left(s"Invalid weapon key '$key'")
      }
    }
  }

  implicit val weaponConfigDecoder: Decoder[WeaponConfig] = deriveDecoder[WeaponConfig]
}
