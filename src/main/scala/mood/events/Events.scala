package mood.events

import mood.sprites.player.guns.{Ammo, WeaponKey}
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

  case class WeaponPickup(weapon: WeaponKey, ammoType: Ammo, remainingAmmo: Int)
  object WeaponPickup {
    val key: EventKey = "weaponpickup"
  }

  case class WeaponChanged(ammoType: Ammo, remaining: Int)
  object WeaponChanged {
    val key: EventKey = "weaponchanged"
  }
}
