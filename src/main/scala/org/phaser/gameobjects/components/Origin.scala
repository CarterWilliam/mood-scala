package org.phaser.gameobjects.components

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Origin[GO <: GameObject] extends js.Any { this: GameObject =>
  def setOrigin(value: Double): GO = js.native
  def setOrigin(x: Double, y: Double): GO = js.native
}
