package org.phaser.gameobjects

import org.phaser.gameobjects.sprite.Sprite

import scala.scalajs.js

@js.native
trait GameObjectFactory extends js.Object {
  def sprite(x: Int, y: Int, texture: String): Sprite = js.native
}
