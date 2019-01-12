package org.phaser.gameobjects.components

import org.phaser.gameobjects.GameObject

import scala.scalajs.js

@js.native
trait Animation extends js.Any { this: GameObject =>

  def isPlaying: Boolean = js.native

  def play(key: String,
           ignoreIfPlaying: Boolean = false,
           startFrame: Int = 0): Animation = js.native

  def stop(): Animation = js.native
}
