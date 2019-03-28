package org.phaser.gameobjects

import org.phaser.gameobjects.graphics.{Graphics, GraphicsOptions}
import org.phaser.gameobjects.particles.ParticleEmitterManager
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.gameobjects.text.{Style, Text}

import scala.scalajs.js

@js.native
trait GameObjectFactory extends js.Object {

  def existing[GO <: GameObject](child: GO): GO = js.native

  // GraphicsFactory
  def graphics(options: GraphicsOptions = GraphicsOptions()): Graphics = js.native

  // ParticleEmitterManager
  def particles(key: String): ParticleEmitterManager = js.native

  // SpriteFactory
  def sprite(x: Int, y: Int, texture: String): Sprite = js.native

  // TextFactory
  def text(
    x: Int,
    y: Int,
    text: String,
    style: js.UndefOr[Style] = js.undefined
  ): Text = js.native
}
