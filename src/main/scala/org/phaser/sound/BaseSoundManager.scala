package org.phaser.sound

import org.phaser.loader.LoaderPlugin.AudioKey

import scala.scalajs.js

@js.native
trait BaseSoundManager extends js.Object {
  def play(key: AudioKey): Boolean = js.native
}
