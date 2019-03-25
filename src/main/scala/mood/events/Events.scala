package mood.events

import mood.sprites.player.guns.Ammo
import org.phaser.events.EventEmitter.EventKey

object Events {

  case class HealthChanged(newValue: Int)
  object HealthChanged {
    val key: EventKey = "healthchanged"
  }

  case class AmmoChanged(`type`: Ammo, amount: Int)
  object AmmoChanged {
    val key: EventKey = "ammochanged"
  }
}
