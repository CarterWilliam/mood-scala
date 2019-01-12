package org.phaser.animations

import org.phaser.loader.LoaderPlugin.AssetKey

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.JSConverters._

@js.native
trait AnimationManager extends js.Object {
  def create(config: AnimationConfig): Animation
  def generateFrameNumbers(key: AssetKey, config: GenerateFrameNumbersConfig): js.Array[AnimationFrameConfig] = js.native
}

@js.native
trait GenerateFrameNumbersConfig extends js.Object {
  def start: Int
  def end: Int
  def first: Boolean
  def outputArray: js.Array[AnimationFrameConfig]
  def frames: Boolean | js.Array[Int]
}

object GenerateFrameNumbersConfig {
  def apply(
    start: Int = 0,
    end: Int = -1,
    first: Boolean = false,
    outputArray: Seq[AnimationFrameConfig] = Nil,
    frames: Option[Seq[Int]] = None
  ): GenerateFrameNumbersConfig = {
    js.Dynamic.literal(
      start = start,
      end = end,
      first = first,
      outputArray = outputArray.toJSArray,
      frames = frames.fold[js.Any](false)(_.toJSArray)
    ).asInstanceOf[GenerateFrameNumbersConfig]
  }
}


@js.native
trait AnimationFrameConfig extends js.Object {
  def key: String
  def frame: String | Int
  def duration: Int
  def visible: Boolean
}
