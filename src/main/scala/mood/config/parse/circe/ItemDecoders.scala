package mood.config.parse.circe

import io.circe.{Decoder, KeyDecoder}
import mood.sprites.items.ItemKey

object ItemDecoders {

  implicit val decodeItemKeyAsKey: KeyDecoder[ItemKey] = new KeyDecoder[ItemKey] {
    override def apply(key: String): Option[ItemKey] = ItemKey(key)
  }

  implicit val decodeItemKey: Decoder[ItemKey] = {
    Decoder.decodeString.emap[ItemKey] { key =>
      ItemKey(key) match {
        case Some(itemKey) => Right(itemKey)
        case None => Left(s"Invalid item key '$key'")
      }
    }
  }

}
