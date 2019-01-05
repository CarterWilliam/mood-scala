package org.phaser.input.keyboard

import scala.scalajs.js

@js.native
trait KeyboardPlugin extends js.Object {
  def createCursorKeys(): CursorKeys = js.native
}

@js.native
trait CursorKeys extends js.Object {
  def up: Key = js.native
  def down: Key = js.native
  def left: Key = js.native
  def right: Key = js.native
  def space: Key = js.native
  def shift: Key = js.native
}