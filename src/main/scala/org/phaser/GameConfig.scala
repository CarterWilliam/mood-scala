package org.phaser

import org.phaser.Phaser.RenderType
import scala.scalajs.js
import org.scalajs.dom.raw._

@js.native
trait Config extends js.Object {
  def width: Int = js.native
  def height: Int = js.native
  def renderType: Int = js.native
  def parent: String = js.native
}

@js.annotation.ScalaJSDefined
trait ConfigImpl extends js.Object {
  def width: Int
  def height: Int
  def renderType: Int
  def parent: String
}

object Config {
  def apply(
    width: Int = 800,
    height: Int = 600,
    renderType: RenderType = RenderType.AUTO,
    parent: Option[String] = None
  ): Config = {
    val _width = width
    val _height = width
    val _renderType = renderType.value
    val _parent = parent.getOrElse(null)

    js.use(new ConfigImpl {
      override val width: Int = _width
      override val height: Int = _height
      override val renderType: Int = _renderType
      override val parent: String = _parent
    }).as[Config]
  }
}
