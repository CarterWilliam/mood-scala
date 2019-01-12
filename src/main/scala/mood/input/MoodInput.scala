package mood.input

import org.phaser.input.keyboard.KeyboardPlugin
import org.phaser.input.keyboard.keys.{Key, KeyCodes}

import scala.scalajs.js

@js.native
trait MoodInput extends js.Object {
  def up: Key = js.native
  def down: Key = js.native
  def left: Key = js.native
  def right: Key = js.native
  def space: Key = js.native
  def shift: Key = js.native

  def pistol: Key = js.native
  def shotgun: Key = js.native
  def chaingun: Key = js.native
  def rocketLauncher: Key = js.native
  def plasmaRifle: Key = js.native
  def bfg9000: Key = js.native
}

object MoodInput {
  def apply(keyboard: KeyboardPlugin): MoodInput = {
    keyboard.addKeys(
      js.Dynamic.literal(
        up = KeyCodes.UP,
        down = KeyCodes.DOWN,
        left = KeyCodes.LEFT,
        right = KeyCodes.RIGHT,
        space = KeyCodes.SPACE,
        shift = KeyCodes.SHIFT,

        pistol = KeyCodes.TWO,
        shotgun = KeyCodes.THREE,
        chaingun = KeyCodes.FOUR,
        rocketLauncher = KeyCodes.FIVE,
        plasmaRifle = KeyCodes.SIX,
        bfg9000 = KeyCodes.SEVEN
      )
    ).asInstanceOf[MoodInput]
  }
}
