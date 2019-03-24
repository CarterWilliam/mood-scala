package mood.animation.circe.codecs

import cats.syntax.either._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.semiauto._
import io.circe.{Decoder, HCursor}
import mood.animation.MoodAnimations.Animation
import org.phaser.animations.AnimationConfig.RepeatConfig
import org.phaser.animations.AnimationConfig.RepeatConfig.{Forever, RepeatConfig}

object AnimationCodecs {

  implicit val decodeConfig: Configuration = Configuration.default.withDefaults

  /**
    * Handles ints ({ "repeat": 3 }) or strings ({ "repeat": "never" })
    */
  implicit val decodeRepeatConfig: Decoder[RepeatConfig] = new Decoder[RepeatConfig] {
    final def apply(c: HCursor): Decoder.Result[RepeatConfig] = {
      ifString(c) orElse ifInt(c)
    }

    private def ifString(c: HCursor): Decoder.Result[RepeatConfig] = {
      c.as[String].map {
        case "never" => RepeatConfig.Never
        case "forever" => RepeatConfig.Forever
      }
    }

    private def ifInt(c: HCursor): Decoder.Result[RepeatConfig] = {
      c.as[Int] map RepeatConfig.apply
    }
  }


  implicit val animationDecoder: Decoder[Animation] = deriveDecoder[Animation]

}
