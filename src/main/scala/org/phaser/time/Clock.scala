package org.phaser.time

import scala.scalajs.js

@js.native
trait Clock extends js.Object {
  def now: Double = js.native
}
