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
  def physics: js.UndefOr[PhysicsConfig]
  def scene: js.Array[Scene] = js.native
}
object GameConfig {
  def apply(
    width: Int = 800,
    height: Int = 600,
    renderType: RenderType = RenderType.AUTO,
    parent: Option[String] = None,
    physics: Option[PhysicsConfig] = None,
    scenes: Seq[Scene] = Nil
  ): GameConfig = {

    js.Dynamic.literal(
      width = width,
      height = height,
      renderType = renderType.value,
      parent = parent.orUndefined,
      physics = physics.orUndefined,
      scene = scenes.toJSArray
    ).asInstanceOf[GameConfig]
  }
}


@js.native
trait PhysicsConfig extends js.Object {
  def default: String = js.native
  def arcade: js.UndefOr[ArcadePhysicsConfig] = js.native
}
object PhysicsConfig {
  def apply(default: String, arcade: Option[ArcadePhysicsConfig] = None): PhysicsConfig = {
    js.Dynamic.literal(
      default = default,
      arcade = arcade.orUndefined
    ).asInstanceOf[PhysicsConfig]
  }
}

@js.native
trait ArcadePhysicsConfig extends js.Object {
  def gravity: Double = js.native
}
object ArcadePhysicsConfig {
  def apply(gravity: Double): ArcadePhysicsConfig = {
    js.Dynamic.literal(gravity = gravity).asInstanceOf[ArcadePhysicsConfig]
  }
}
