package mood.events

import org.phaser.events.EventEmitter.EventKey


object Events {

  case class HealthChanged(newValue: Int)
  object HealthChanged {
    val key: EventKey = "healthchanged"
  }
}
