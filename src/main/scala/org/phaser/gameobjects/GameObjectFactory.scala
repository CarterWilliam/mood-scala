package org.phaser.gameobjects

import org.phaser.gameobjects.graphics.{Graphics, GraphicsOptions}
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.gameobjects.text.Text

import scala.scalajs.js

@js.native
trait GameObjectFactory extends js.Object {

  def existing(child: GameObject): GameObject = js.native

  // GraphicsFactory
  def graphics(options: GraphicsOptions = GraphicsOptions()): Graphics = js.native

  // SpriteFactory
  def sprite(x: Int, y: Int, texture: String): Sprite = js.native

  // TextFactory
  def text(
    x: Int,
    y: Int,
    text: String,
    style: js.UndefOr[js.Object] = js.undefined
  ): Text = js.native
}
