package org.phaser.physics.arcade

import scala.scalajs.js

@js.native
trait Body extends js.Object {
  def setCollideWorldBounds(value: Boolean = true): Body = js.native
  def setOffset(x: Int, y: Int): Body = js.native
  def setSize(width: Int, height: Int, center: Boolean = true): Body = js.native
  def setVelocity(x: Double, y: Double): Body = js.native
  def setVelocityX(value: Double): Body = js.native
  def setVelocityY(value: Double): Body = js.native
}
