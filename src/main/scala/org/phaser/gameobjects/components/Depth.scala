package org.phaser.gameobjects.components

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Depth extends js.Any { this: GameObject =>
  def setDepth(value: Int): GameObject = js.native
}
