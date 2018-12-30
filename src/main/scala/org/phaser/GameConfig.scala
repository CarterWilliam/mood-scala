package org.phaser

import org.phaser.Phaser.RenderType
import org.phaser.scenes.Scene

import scala.scalajs.js
import js.JSConverters._

@js.native
trait GameConfig extends js.Object {
  def width: Int = js.native
  def height: Int = js.native
  def renderType: Int = js.native
  def parent: js.UndefOr[String] = js.native
  def scene: js.Array[Scene] = js.native
}

object GameConfig {
  def apply(
    width: Int = 800,
    height: Int = 600,
    renderType: RenderType = RenderType.AUTO,
    parent: Option[String] = None,
    scenes: Seq[Scene] = Nil
  ): GameConfig = {

    js.Dynamic.literal(
      width = width,
      height = height,
      renderType = renderType.value,
      parent = parent.orUndefined,
      scene = scenes.toJSArray
    ).asInstanceOf[GameConfig]
  }
}
