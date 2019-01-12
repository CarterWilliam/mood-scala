package org.phaser.gameobjects.sprite

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Animation, Depth, Origin, Transform}
import org.phaser.scenes.Scene

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.GameObjects.Sprite")
class Sprite(
  scene: Scene,
  x: Int,
  y: Int,
  texture: String,
  frame: js.UndefOr[String] = js.undefined
) extends GameObject
  with Depth[Sprite]
  with Origin[Sprite]
  with Transform[Sprite] {

  def anims: Animation = js.native
}
