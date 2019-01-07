package org.phaser.gameobjects.sprite

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Depth, Origin, Transform}
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
  with Depth
  with Origin
  with Transform
