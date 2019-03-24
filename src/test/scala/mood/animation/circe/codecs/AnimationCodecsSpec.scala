package mood.animation.circe.codecs

import io.circe.generic.extras.Configuration
import io.circe.parser._
import mood.animation.MoodAnimations.Animation
import mood.animation.MoodAnimations.Animation.AnimationKey
import org.phaser.animations.AnimationConfig.RepeatConfig.{Forever, Repeat, RepeatConfig}
import org.scalatest.{Matchers, WordSpec}

class AnimationCodecsSpec extends WordSpec with Matchers {

  import AnimationCodecs._

  "The AnimationCodecs" should {

    "parse animations with a string repeat" in {
      val json =
        """
          |{
          |  "key": "anikey",
          |  "frames": [ 1, 2, 3 ],
          |  "frameRate": 2,
          |  "repeat": "forever"
          |}
        """.stripMargin

      decode[Animation](json) shouldBe Right(
        Animation(
          key = "anikey",
          frames = Seq(1, 2, 3),
          repeat = Forever,
          frameRate = 2
        )
      )
    }

    "parse animations with an integer repeat" in {
      val json =
        """
          |{
          |  "key": "anikey",
          |  "frames": [ 1, 2, 3 ],
          |  "frameRate": 2,
          |  "repeat": 7
          |}
        """.stripMargin

      decode[Animation](json) shouldBe Right(
        Animation(
          key = "anikey",
          frames = Seq(1, 2, 3),
          repeat = Repeat(7),
          frameRate = 2
        )
      )
    }

    "parse animations with a default repeat" in {
      val json =
        """
          |{
          |  "key": "anikey",
          |  "frames": [ 1, 2, 3 ],
          |  "frameRate": "1"
          |}
        """.stripMargin

      decode[Animation](json) shouldBe Right(
        Animation(
          key = "anikey",
          frames = Seq(1, 2, 3),
          repeat = Forever,
          frameRate = 1
        )
      )
    }

    "parse animations with a default frame rate" in {
      val json =
        """
          |{
          |  "key": "anikey",
          |  "frames": [ 1, 2, 3 ],
          |  "repeat": "forever"
          |}
        """.stripMargin

      decode[Animation](json) shouldBe Right(
        Animation(
          key = "anikey",
          frames = Seq(1, 2, 3),
          repeat = Forever,
          frameRate = 10
        )
      )
    }

  }
}
