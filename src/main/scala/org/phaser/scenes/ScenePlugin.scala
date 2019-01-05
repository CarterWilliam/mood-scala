package org.phaser.scenes

import scala.scalajs.js

@js.native
trait ScenePlugin extends js.Object {
  def start[S <: Scene](id: String, data: js.UndefOr[S#Data] = js.undefined): ScenePlugin = js.native
}
