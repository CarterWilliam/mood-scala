package mood.scenes

import cats.data.NonEmptyList
import mood.Assets
import mood.scenes.MenuScene.Depth
import monocle.Lens
import monocle.macros.GenLens
import org.phaser.input.keyboard.CursorKeys
import org.phaser.scenes.Scene.SceneKey
import org.phaser.scenes.{Scene, SceneConfig}

import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
class MenuScene extends Scene(MenuScene.Config) {
  var menu: MenuController = _
  var keyboard: CursorKeys = _
  var lastMenuEvent: Double = _

  override def preload(): Unit = {
    val background = add.sprite(0, 0, Assets.Textures.Splash.key)
    background.setOrigin(0,0)
    background.setDepth(Depth.MenuBackground)
  }

  override def create(): Unit = {
    menu = new MenuController(scene = this)
    keyboard = input.keyboard.createCursorKeys()
    lastMenuEvent = 0
  }

  override def update(time: Double, delta: Double): Unit = {
    if (canUpdateMenu(time)) {
      if (keyboard.space.isDown) {
        sound.play(Assets.Audio.Pistol.key)
        lastMenuEvent = time
      }
    }
  }

  private def canUpdateMenu(now: Double): Boolean = {
    (now - lastMenuEvent) > MenuScene.Speed.toMillis
  }

}

object MenuScene {
  val Config = SceneConfig("menu")

  object Depth {
    val MenuBackground = 0
    val MenuText = 1
  }

  val Speed: FiniteDuration = 100.milliseconds

  val InitialMenu = Menu(
    MenuChoice(
      MenuOption("Start Game", SubMenu(
        MenuOption("I'm too young to die.", DoAction(() => (): Unit)),
        MenuOption("Not too rough.", DoAction(() => (): Unit)),
        MenuOption("Hurt me plenty.", DoAction(() => (): Unit)),
        MenuOption("Ultra-Violence.", DoAction(() => (): Unit)),
        MenuOption("NIGHTMARE!", StartScene("loading"))
      ))
    )
  )

}

class MenuController(scene: Scene) {
  var menu: Menu = MenuScene.InitialMenu
  drawChoice()

  def up(): Unit = ???
  def down(): Unit = ???
  def select(): Unit = ???

  private def drawChoice(): Unit = {

  }

  private def drawSkull(position: Int): Unit = {

  }
}

case class Menu(choice: MenuChoice, history: Seq[MenuChoice] = Nil) {

  def up(): Menu = copy(choice = choice.decrement())

  def down(): Menu = copy(choice = choice.increment())

  def select(): Menu = selected.action match {
    case sub: SubMenu =>
      copy(choice = MenuChoice(sub.options), history = history :+ choice)
    case DoAction(action) =>
      action()
      this
  }

  def back(): Menu = history match {
    case Nil => this
    case head :: tail =>
      copy(choice = head, history = tail)
  }

  def selected: MenuOption = {
    val selectedIndex = (choice.selected + choice.options.size) % choice.options.size
    choice.options.toList(selectedIndex)
  }
}

case class MenuChoice(options: NonEmptyList[MenuOption], selected: Int = 0) {

  def increment(): MenuChoice = {
    copy(selected = normalise(selected + 1))
  }

  def decrement(): MenuChoice = {
    copy(selected = normalise(selected - 1))
  }

  private def normalise(n: Int): Int = (n + options.size) % options.size
}
object MenuChoice {
  def apply(head: MenuOption, tail: MenuOption*): MenuChoice = {
    MenuChoice(options = NonEmptyList(head, tail.toList))
  }
}

case class MenuOption(text: String, action: MenuAction)

sealed trait MenuAction
case class SubMenu(options: NonEmptyList[MenuOption]) extends MenuAction
object SubMenu {
  def apply(head: MenuOption, tail: MenuOption*): SubMenu = {
    SubMenu(options = NonEmptyList(head, tail.toList))
  }
}

case class DoAction(action: () => Unit) extends MenuAction
case class StartScene(sceneId: SceneKey) extends MenuAction
