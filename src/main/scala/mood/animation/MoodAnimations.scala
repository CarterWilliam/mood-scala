package mood.animation

import mood.Assets
import mood.sprites.Player
import org.phaser.animations.AnimationConfig.RepeatConfig.{Forever, Repeat}
import org.phaser.animations.{AnimationConfig, AnimationManager, GenerateFrameNumbersConfig}

object MoodAnimations {
  case class Animation(key: String, frames: Seq[Int])

  def createAnimations(anims: AnimationManager): Unit = {
    Player.Animations.all.foreach { animation =>
      anims.create(AnimationConfig(
        key = animation.key,
        frames = anims.generateFrameNumbers(Assets.SpriteSheets.Player.key, GenerateFrameNumbersConfig(frames = Some(animation.frames))),
        frameRate = Some(10),
        repeat = Repeat(Forever.times)
      ))
    }
  }
}
