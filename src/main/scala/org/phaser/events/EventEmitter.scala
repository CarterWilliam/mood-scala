package org.phaser.events

import org.phaser.events.EventEmitter.EventKey

import scala.scalajs.js

@js.native
class EventEmitter extends js.Object {
  def on(event: EventKey, fn: js.Function, context: js.Any = this): EventEmitter = js.native
}

object EventEmitter {
  type EventKey = String
}