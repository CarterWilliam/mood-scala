package org.phaser.physics.arcade

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Factory extends js.Object {
  def collider[Object1 <: GameObject, Object2 <: GameObject](
    object1: Object1,
    object2: Object2,
    collideCallback: js.UndefOr[js.Function2[Object1, Object2, Boolean]] = js.undefined,
    processCallback: js.UndefOr[js.Function2[Object1, Object2, Boolean]] = js.undefined,
    callbackContext: js.UndefOr[js.Function2[Object1, Object2, Boolean]] = js.undefined): Collider
}
