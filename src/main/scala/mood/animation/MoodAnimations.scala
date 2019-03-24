package mood.animation

import mood.animation.MoodAnimations.Animation.AnimationKey
import mood.config.GameConfig
import mood.sprites.Player
import mood.util.Direction._
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

  case class DirectedAnimations(
    north: Animation,
    northEast: Animation,
    east: Animation,
    southEast: Animation,
    south: Animation,
    southWest: Animation,
    west: Animation,
    northWest: Animation
  ) {
    def apply(direction: Direction): Animation = direction match {
      case North => north
      case NorthEast => northEast
      case East => east
      case SouthEast => southEast
      case South => south
      case SouthWest => southWest
      case West => west
      case NorthWest => northWest
    }

    def foreach(f: Animation => Unit): Unit = {
      Seq(north, northEast, east, southEast, south, southWest, west, northWest).foreach(f)
    }
  }

  def createAnimations(anims: AnimationManager, gameConfig: GameConfig): Unit = {
    Player.Animations.all.foreach(createAnimation(anims, "player"))
    gameConfig.enemies.foreach { case (enemyKey, enemyConfig) =>
      createAnimation(anims, enemyKey)(enemyConfig.animations.passive)
      createAnimation(anims, enemyKey)(enemyConfig.animations.die)
      enemyConfig.animations.movement.foreach(createAnimation(anims, enemyKey))
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
