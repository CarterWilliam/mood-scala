package mood.events

import mood.sprites.player.guns.Ammo
import org.phaser.events.EventEmitter.EventKey

object Events {

  case class HealthChanged(newValue: Int)
  object HealthChanged {
    val key: EventKey = "healthchanged"
  }

  case class AmmoChanged(ammoType: Ammo, amount: Int)
  object AmmoChanged {
    val key: EventKey = "ammochanged"
  }

  case class WeaponChanged(ammoType: Ammo, remaining: Int)
  object WeaponChanged {
    val key: EventKey = "weaponchanged"
  }
}
