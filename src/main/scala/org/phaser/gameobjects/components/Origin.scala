package org.phaser.gameobjects.components

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Origin extends js.Any { this: GameObject =>
  def setOrigin(x: Int, y: Int): GameObject = js.native
}
