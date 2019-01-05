package org.phaser.scenes

import scala.scalajs.js

@js.native
trait ScenePlugin extends js.Object {
  def add(key: String, scene: Scene): ScenePlugin = js.native
  def start[S <: Scene](key: String, data: js.UndefOr[S#Data] = js.undefined): ScenePlugin = js.native
}
