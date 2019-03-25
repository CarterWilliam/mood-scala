package org.phaser.gameobjects.sprite

import org.phaser.events.EventEmitter
import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Animation, Depth, Origin, Transform}
import org.phaser.scenes.Scene

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.GameObjects.Sprite")
class Sprite(
  scene: Scene,
  x: Double,
  y: Double,
  texture: String,
  frame: js.UndefOr[String] = js.undefined
) extends GameObject
  with Depth[Sprite]
  with Origin[Sprite]
  with Transform[Sprite] {

  def anims: Animation = js.native
}

object Sprite {
  class SpriteExtension(sprite: Sprite) {
    def onAnimationComplete(f: => Unit): EventEmitter = {
      sprite.once("animationcomplete", { _: Any => f })
    }
  }

  implicit def extend(sprite: Sprite): SpriteExtension = new SpriteExtension(sprite)
}
