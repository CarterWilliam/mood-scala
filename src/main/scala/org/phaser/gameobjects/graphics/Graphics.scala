package org.phaser.gameobjects.graphics

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Depth, Transform}

import scala.scalajs.js

@js.native
trait Graphics extends GameObject
  with Depth
  with Transform {

  def clear(): Graphics = js.native
  def fillStyle(color: Int, alpha: Double = 1.0): Graphics = js.native
  def fillRect(x: Double, y: Double, width: Double, height: Double): Graphics = js.native
  def strokeRect(x: Double, y: Double, width: Double, height: Double): Graphics = js.native
}

@js.native
trait GraphicsLineStyle extends js.Object {
  def width: Int = js.native
  def color: Int = js.native
  def alpha: Double = js.native
}

object GraphicsLineStyle {
  def apply(width: Int, color: Int, alpha: Double = 1.0): GraphicsLineStyle =
    js.Dynamic.literal(width = width, color = color, alpha = alpha).asInstanceOf[GraphicsLineStyle]
}

@js.native
trait GraphicsFillStyle extends js.Object {
  def color: Int = js.native
  def alpha: Double = js.native
}

@js.native
trait GraphicsStyles extends js.Object {
  def lineStyle: GraphicsLineStyle = js.native
  def fillStyle: GraphicsFillStyle = js.native
}

@js.native
trait GraphicsOptions extends GraphicsStyles {
  def x: Int = js.native
  def y: Int = js.native
}

object GraphicsOptions {
  def apply(
    x: js.UndefOr[Int] = js.undefined,
    y: js.UndefOr[Int] = js.undefined,
    lineStyle: js.UndefOr[GraphicsLineStyle] = js.undefined,
    fillStyle: js.UndefOr[GraphicsFillStyle] = js.undefined
  ): GraphicsOptions = js.Dynamic.literal(
    x = x,
    y = y,
    lineStyle = lineStyle,
    fillStyle = fillStyle
  ).asInstanceOf[GraphicsOptions]
}
