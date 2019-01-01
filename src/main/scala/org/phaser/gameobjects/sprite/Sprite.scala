package org.phaser.gameobjects.sprite

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Depth, Origin, Transform}

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.GameObjects.Sprite")
class Sprite extends GameObject
  with Depth
  with Origin
  with Transform
