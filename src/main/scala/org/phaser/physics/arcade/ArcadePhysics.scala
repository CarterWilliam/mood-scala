package org.phaser.physics.arcade

import scala.scalajs.js

@js.native
trait ArcadePhysics extends js.Object {
  def add: Factory
  def world: World
}
