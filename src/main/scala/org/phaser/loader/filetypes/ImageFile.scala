package org.phaser.loader.filetypes

import scala.scalajs.js

@js.native
trait ImageFile extends js.Object

@js.native
trait ImageFrameConfig extends js.Object {
  def frameWidth: Int = js.native
  def frameHeight: js.UndefOr[Int] = js.native
  def startFrame: Int = js.native
  def endFrame: js.UndefOr[Int] = js.native
  def margin: Int = js.native
  def spacing: Int = js.native
}
object ImageFrameConfig {
  def apply(
    frameWidth: Int,
    frameHeight: js.UndefOr[Int] = js.undefined,
    startFrame: Int = 0,
    endFrame: js.UndefOr[Int] = js.undefined,
    margin: Int = 0,
    spacing: Int = 0
  ): ImageFrameConfig = {
    js.Dynamic.literal(
      frameWidth = frameWidth,
      frameHeight = frameHeight,
      startFrame = startFrame,
      endFrame = endFrame,
      margin = margin,
      spacing = spacing
    ).asInstanceOf[ImageFrameConfig]
  }
}
