package org.phaser.input.keyboard

import scala.scalajs.js

@js.native
trait Key extends js.Object {
  def altKey: Boolean = js.native
  def ctrlKey: Boolean = js.native
  def duration: Long = js.native
  def enabled: Boolean = js.native
  def isDown: Boolean = js.native
  def isUp: Boolean = js.native
  def keyCode: Int = js.native
  def location: Int = js.native
  def preventDefault: Boolean = js.native
  def shiftKey: Boolean = js.native
  def timeDown: Long = js.native
  def timeUp: Long = js.native
}
