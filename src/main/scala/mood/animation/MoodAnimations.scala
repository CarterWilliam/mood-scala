package mood.animation

import mood.Assets
import mood.animation.MoodAnimations.Animation.AnimationKey
import mood.sprites.Player
import org.phaser.animations.AnimationConfig.RepeatConfig.{Forever, Repeat, RepeatConfig}
import org.phaser.animations.{AnimationConfig, AnimationManager, GenerateFrameNumbersConfig}

object MoodAnimations {
  case class Animation(key: AnimationKey, frames: Seq[Int], repeat: RepeatConfig)
  object Animation {
    type AnimationKey = String
    implicit def animationKey(animation: Animation): AnimationKey = animation.key
  }

  def createAnimations(anims: AnimationManager): Unit = {
    Player.Animations.all.foreach { animation =>
      anims.create(AnimationConfig(
        key = animation.key,
        frames = anims.generateFrameNumbers(Assets.SpriteSheets.Player.key, GenerateFrameNumbersConfig(frames = Some(animation.frames))),
        frameRate = Some(10),
        repeat = animation.repeat
      ))
    }
  }
}
