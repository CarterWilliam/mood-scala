package org.phaser.animations

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

@js.native
trait Animation extends js.Object

@js.native
trait AnimationConfig extends js.Object {
  def key: String
  def frames: js.Array[AnimationFrameConfig]
  def defaultTextureKey: js.UndefOr[String]
  def frameRate: js.UndefOr[Int]
  def duration: js.UndefOr[Int]
  def skipMissedFrames: Boolean
  def delay: Int
  def repeat: Int
  def repeatDelay: Int
  def yoyo: Boolean
  def showOnStart: Boolean
  def hideOnComplete: Boolean
}

object AnimationConfig {
  def apply(
    key: String,
    frames: Seq[AnimationFrameConfig],
    defaultTextureKey: Option[String] = None,
    frameRate: Option[Int] = None,
    duration: Option[Int] = None,
    skipMissedFrames: Boolean = false,
    delay: Int = 0,
    repeat: Int = 0,
    repeatDelay: Int = 0,
    yoyo: Boolean = false,
    showOnStart: Boolean = false,
    hideOnComplete: Boolean = false
  ): AnimationConfig = {
    js.Dynamic.literal(
      key = key,
      frames = frames.toJSArray,
      defaultTextureKey = defaultTextureKey.orUndefined,
      frameRate = frameRate.orUndefined,
      duration = duration.orUndefined,
      skipMissedFrames = skipMissedFrames,
      delay = delay,
      repeat = repeat,
      repeatDelay = repeatDelay,
      yoyo = yoyo,
      showOnStart = showOnStart,
      hideOnComplete = hideOnComplete,
    ).asInstanceOf[AnimationConfig]
  }
}
