package org.phaser.gameobjects.components

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Transform extends js.Any { this: GameObject =>
  def setPosition(x: Int, y: Int): GameObject = js.native
}
