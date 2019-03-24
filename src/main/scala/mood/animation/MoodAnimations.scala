package mood.animation

import mood.animation.MoodAnimations.Animation.AnimationKey
import mood.config.GameConfig
import mood.sprites.Player
import org.phaser.animations.AnimationConfig.RepeatConfig.{Forever, Repeat, RepeatConfig}
import org.phaser.animations.{AnimationConfig, AnimationManager, GenerateFrameNumbersConfig}
import org.phaser.loader.LoaderPlugin.AssetKey

object MoodAnimations {
  case class Animation(
    key: AnimationKey,
    frames: Seq[Int],
    repeat: RepeatConfig = Forever,
    frameRate: Int = 10
  )
  object Animation {
    type AnimationKey = String
    implicit def animationKey(animation: Animation): AnimationKey = animation.key
  }

  def createAnimations(anims: AnimationManager, gameConfig: GameConfig): Unit = {
    Player.Animations.all.foreach(createAnimation(anims, "player"))
    gameConfig.enemies.foreach { case (enemyKey, enemyConfig) =>
      createAnimation(anims, enemyKey)(enemyConfig.animations.passive)
      createAnimation(anims, enemyKey)(enemyConfig.animations.die)
    }
  }

  private def createAnimation(anims: AnimationManager, assetKey: AssetKey)(animation: Animation): Unit = {
    anims.create(AnimationConfig(
      key = animation.key,
      frames = anims.generateFrameNumbers(assetKey, GenerateFrameNumbersConfig(frames = Some(animation.frames))),
      frameRate = Some(animation.frameRate),
      repeat = animation.repeat
    ))
  }
}
