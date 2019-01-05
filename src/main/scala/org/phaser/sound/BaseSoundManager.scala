package org.phaser.sound

import org.phaser.loader.LoaderPlugin.AssetKey

import scala.scalajs.js

@js.native
trait BaseSoundManager extends js.Object {
  def play(key: AssetKey): Boolean = js.native
}
