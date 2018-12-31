package org.phaser.input

import org.phaser.input.keyboard.KeyboardPlugin

import scala.scalajs.js

@js.native
trait InputPlugin extends js.Object {
  def keyboard: KeyboardPlugin = js.native
}
