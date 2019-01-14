package org.phaser.gameobjects.text

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Depth, Origin, Transform}
import org.phaser.gameobjects.text.Style.Color
import org.phaser.scenes.Scene

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

@js.native
@js.annotation.JSGlobal("Phaser.GameObjects.Text")
class Text(scene: Scene, x: Int, y: Int, text: String, style: Style) extends GameObject
  with Depth[Text]
  with Origin[Text]
  with Transform[Text] {

  def setColor(color: Color): Text = js.native
  def setStyle(style: Style): Text = js.native
  def setText(value: String): Text = js.native
}

@js.native
trait Style extends js.Object {
  def color: js.UndefOr[String]
  def font: js.UndefOr[String]
  def stroke: js.UndefOr[String]
  def strokeThickness: js.UndefOr[Int]
}

object Style {
  type Color = String

  def apply(
    align: Align = Left,
    color: Option[Color] = None,
    font: Option[String] = None,
    stroke: Option[Color] = None,
    strokeThickness: Option[Int] = None
  ): Style = {
    js.Dynamic.literal(
      align = align.value,
      color = color.orUndefined,
      font = font.orUndefined,
      stroke = stroke.orUndefined,
      strokeThickness = strokeThickness.orUndefined
    ).asInstanceOf[Style]
  }

  sealed trait Align { private[Style] def value: String }
  case object Left extends Align { val value = "left" }
  case object Right extends Align { val value = "right" }
}
