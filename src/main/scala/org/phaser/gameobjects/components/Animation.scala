package org.phaser.gameobjects.components

import scala.scalajs.js

@js.native
trait Animation extends js.Object {
  def play(key: String,
           ignoreIfPlaying: Boolean = false,
           startFrame: Int = 0): Animation = js.native
}
