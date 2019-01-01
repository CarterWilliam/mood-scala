package org.phaser.gameobjects.text

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Depth, Origin, Transform}

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.GameObjects.Text")
class Text extends GameObject
  with Depth
  with Origin
  with Transform

