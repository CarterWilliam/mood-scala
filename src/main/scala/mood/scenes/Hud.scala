package mood.scenes

import mood.events.Events.{AmmoChanged, HealthChanged}
import mood.scenes.Hud.{AmmoAmounts, Arms, Depth, Fonts}
import mood.sprites.player.guns._
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.gameobjects.text.Style.Color
import org.phaser.gameobjects.text.{Style, Text}
import org.phaser.scenes.{Scene, SceneConfig}
import org.phaser.scenes.Scene.SceneKey

class Hud extends Scene(Hud.Config) {
  var health: Text = _
  var currentAmmo: Text = _
  var armour: Text = _
  var arms: Arms = _
  var ammo: AmmoAmounts = _

  override def create(): Unit = {
    val background: Sprite = add.sprite(0, 520, "hud")
    background.setOrigin(0, 0)
    background.setDepth(Depth.Background)

    health = createHudText(194, Hud.LargeGaugeY, "100%", Fonts.LargeGaugeRed)
    currentAmmo = createHudText(66, Hud.LargeGaugeY, "50", Fonts.LargeGaugeRed)
    armour = createHudText(522, Hud.LargeGaugeY, "0%", Fonts.LargeGaugeRed)

    arms = Arms(
      two = add.existing(new HasArmDisplay(this, 273, 525, "2").acquired()),
      three = add.existing(new HasArmDisplay(this, 303, 525, "3")),
      four = add.existing(new HasArmDisplay(this, 333, 525, "4")),
      five = add.existing(new HasArmDisplay(this, 273, 550, "5")),
      six = add.existing(new HasArmDisplay(this, 303, 550, "6")),
      seven = add.existing(new HasArmDisplay(this, 333, 550, "7")))

    ammo = AmmoAmounts(
      bullets = new AmmoAmountsDisplay(this, 50, 200, 526),
      shells = new AmmoAmountsDisplay(this, 0, 50, 542),
      rockets = new AmmoAmountsDisplay(this, 0, 50, 558),
      plasma = new AmmoAmountsDisplay(this, 0, 200, 574),
    )
  }

  private def createHudText(x: Int, y: Int, initial: String, font: Style): Text = {
    val text = add.text(x, y, initial, font)
    text.setOrigin(0.5)
    text.setDepth(Depth.Display)
  }

  def startWatching(scene: GameScene): Unit = {
    scene.events.on(HealthChanged.key, { healthChanged: HealthChanged =>
      health.setText(s"${healthChanged.newValue.toString}%")
    })
    scene.events.on(AmmoChanged.key, { ammoChanged: AmmoChanged =>
      ammo(ammoChanged.`type`).remainingDisplay
        .setText(s"${ammoChanged.amount.toString}%")
    })
  }

}

object Hud {
  case object Key extends SceneKey { val value = "key" }
  val Config = SceneConfig(Key)

  object Depth {
    val Background: Int = 0
    val Display: Int = 1
  }

  val LargeGaugeY: Int = 553

  object Fonts {
    val red: Color =  "#BB0000"
    val yellow: Color =  "#FFFF73"
    val grey: Color =  "#999999"
    val black: Color =  "#000000"

    val LargeGaugeRed = Style(
      color = Some(red),
      font = Some("24pt Doom"),
      stroke = Some(black),
      strokeThickness = Some(4)
    )
    val SmallGaugeYellow = Style(
      color = Some(yellow),
      font = Some("14pt Square"),
      stroke = Some(black),
      strokeThickness = Some(4)
    )

    val SmallGaugeGrey = Style(
      color = Some(grey),
      font = Some("14pt Square"),
      stroke = Some(black),
      strokeThickness = Some(4)
    )
  }

  case class Arms(
    two: HasArmDisplay,
    three: HasArmDisplay,
    four: HasArmDisplay,
    five: HasArmDisplay,
    six: HasArmDisplay,
    seven: HasArmDisplay)

  case class AmmoAmounts(
    bullets: AmmoAmountsDisplay,
    shells: AmmoAmountsDisplay,
    rockets: AmmoAmountsDisplay,
    plasma: AmmoAmountsDisplay) {

    def apply(`type`: Ammo): AmmoAmountsDisplay = `type` match {
      case Bullets => bullets
      case Shells => shells
      case Rockets => rockets
      case Plasma => plasma
    }
  }
}

class HasArmDisplay(hud: Hud, x: Int, y: Int, label: String) extends Text(hud, x, y, label, Fonts.SmallGaugeGrey) {
  def acquired(): HasArmDisplay = setColor(Fonts.yellow).asInstanceOf[HasArmDisplay]
}

class AmmoAmountsDisplay(hud: Scene, initial: Int, max: Int, y: Int) {
  val remainingDisplay: Text = hud.add.text(690, y, initial.toString, Fonts.SmallGaugeYellow)
  val maxDisplay: Text = hud.add.text(750, y, max.toString, Fonts.SmallGaugeYellow)
}
