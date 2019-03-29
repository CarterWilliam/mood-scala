package mood.config.parse.circe

import io.circe.generic.extras.Configuration
import io.circe.{Decoder, KeyDecoder}
import io.circe.generic.extras.semiauto.deriveDecoder
import mood.sprites.player.guns.{WeaponConfig, WeaponKey}
import mood.sprites.projectiles.Projectile

object WeaponDecoders {
  import WithDefaultsConfig._
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

  implicit val projectileConfigDecoder: Decoder[Projectile.Config] = deriveDecoder[Projectile.Config]
  implicit val weaponConfigDecoder: Decoder[WeaponConfig] = deriveDecoder[WeaponConfig]
}
