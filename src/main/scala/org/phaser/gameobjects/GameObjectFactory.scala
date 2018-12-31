package org.phaser.gameobjects

import org.phaser.gameobjects.sprite.Sprite
import org.phaser.gameobjects.text.Text

import scala.scalajs.js

@js.native
trait GameObjectFactory extends js.Object {

  def sprite(x: Int, y: Int, texture: String): Sprite = js.native

  def text(
    x: Int,
    y: Int,
    text: String,
    style: js.UndefOr[js.Object] = js.undefined
  ): Text = js.native
}
