package org.phaser.gameobjects.components

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Transform[GO <: GameObject] extends js.Any { this: GameObject =>

  def x: Double = js.native
  def y: Double = js.native

  def setPosition(x: Int, y: Int): GO = js.native
}
