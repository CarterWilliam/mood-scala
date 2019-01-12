package org.phaser.gameobjects.components

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Depth[GO <: GameObject] extends js.Any { this: GameObject =>
  def setDepth(value: Int): GO = js.native
}
