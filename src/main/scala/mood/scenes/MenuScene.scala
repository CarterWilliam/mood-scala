package mood.scenes

import cats.data.NonEmptyList
import mood.Assets
import mood.config.{LevelAsset, LevelAssets, LevelConfig}
import mood.scenes.LoadScene.LoadId
import mood.scenes.MenuScene.{Depth, MenuId}
import org.phaser.gameobjects.sprite.Sprite
import org.phaser.gameobjects.text.Text
import org.phaser.input.keyboard.CursorKeys
import org.phaser.scenes.Scene.{NoData, SceneKey}
import org.phaser.scenes.{Scene, SceneConfig}

import scala.concurrent.duration.{DurationInt, FiniteDuration}
import scala.scalajs.js

class MenuScene extends Scene(MenuScene.Config) {
  override type Key = MenuId.type
  override type Data = NoData

  var menu: MenuController = _
  var keyboard: CursorKeys = _
  var lastMenuEvent: Double = _

  override def preload(): Unit = {
    val background = add.sprite(0, 0, Assets.Textures.Splash.key)
    background.setOrigin(0,0)
    background.setDepth(Depth.MenuBackground)
  }

  override def create(): Unit = {
    menu = new MenuController(menuScene = this)
    keyboard = input.keyboard.createCursorKeys()
    lastMenuEvent = 0
  }

  override def update(time: Double, delta: Double): Unit = {
    def doAction(action: => Unit): Unit = {
      sound.play(Assets.Audio.Pistol.key)
      lastMenuEvent = time
      action
    }

    if (canUpdateMenu(time)) {
      if (keyboard.down.isDown) {
        doAction {
          menu.down()
        }
      } else if (keyboard.up.isDown) {
        doAction {
          menu.up()
        }
      } else if (keyboard.left.isDown) {
        doAction {
          menu.back()
        }
      } else if (keyboard.right.isDown || keyboard.space.isDown) {
        doAction {
          menu.select()
        }
      }
    }
  }

  private def canUpdateMenu(now: Double): Boolean = {
    (now - lastMenuEvent) > MenuScene.Speed.toMillis
  }

}

object MenuScene {
  case object MenuId extends SceneKey { val value = "menu" }
  val Config = SceneConfig("menu")

  object Depth {
    val MenuBackground = 0
    val MenuText = 1
  }

  val Speed: FiniteDuration = 100.milliseconds

  val InitialMenu = MenuModel(
    MenuChoice(
      MenuOption("New Game", SubMenu(
        MenuOption("I'm too young to die.", DoAction(() => (): Unit)),
        MenuOption("Not too rough.", DoAction(() => (): Unit)),
        MenuOption("Hurt me plenty.", DoAction(() => (): Unit)),
        MenuOption("Ultra-Violence.", DoAction(() => (): Unit)),
        MenuOption("NIGHTMARE!", StartScene(LoadId))
      )),
      MenuOption("Options", DoAction(() => (): Unit)),
      MenuOption("Load Game", DoAction(() => (): Unit)),
      MenuOption("Save Game", DoAction(() => (): Unit)),
      MenuOption("Quit Game", DoAction(() => (): Unit))
    )
  )

  val LevelOneConfig = new LevelConfig(
    key = "level-one",
    assets = LevelAssets(
      tilemaps = Seq(LevelAsset("map-futuristic", "assets/maps/futuristic.json")),
      images = Seq(LevelAsset("tiles-futuristic", "assets/maps/futuristic.png"))),
    scenes = Nil)
}

class MenuController(menuScene: MenuScene) {
  import MenuController._

  private var model: MenuModel = MenuScene.InitialMenu
  private var visibleOptions: Seq[Text] = Nil
  private var cursor: Sprite = menuScene.add.sprite(
    x = MenuX + CursorOffsetX,
    y = MenuY + CursorOffsetY,
    texture = Assets.Textures.MenuSkull.key)
  cursor.setDepth(MenuScene.Depth.MenuText)

  drawChoice()
  moveCursor()

  def up(): Unit = {
    model = model.up()
    moveCursor()
  }

  def down(): Unit = {
    model = model.down()
    moveCursor()
  }

  def select(): Unit = {
    model.selected.action match {
      case _: SubMenu =>
        model = model.select()
        drawChoice()
      case StartScene(_) =>
        menuScene.scene.start[LoadScene](LoadId, MenuScene.LevelOneConfig)
    }
  }

  def back(): Unit = {
    val updated = model.back()
    if (updated != model) {
      model = updated
      drawChoice()
    }
  }

  private def drawChoice(): Unit = {
    visibleOptions.foreach(_.destroy())
    visibleOptions = model.choice.options.toList.zipWithIndex.map { case(option, index) =>
      val text = menuScene.add.text(MenuX, MenuY + index*TextSpacing, option.text, TextStyle)
      text.setDepth(MenuScene.Depth.MenuText)
      text
    }
  }

  private def moveCursor(): Unit = {
    cursor.setPosition(
      x = MenuX + CursorOffsetX,
      y = MenuY + CursorOffsetY + model.choice.selected * TextSpacing)
  }
}

object MenuController {
  val MenuX: Int = 200
  val MenuY: Int = 300

  val TextSpacing: Int = 40

  val CursorOffsetX: Int = -30
  val CursorOffsetY: Int = 20

  val TextStyle: js.Object = js.Dynamic.literal(
    color = "#BB0000",
    align = "left",
    font = "28pt Doom",
    stroke = "#000000",
    strokeThickness = 4
  )
}

case class MenuModel(choice: MenuChoice, history: Seq[MenuChoice] = Nil) {

  def up(): MenuModel = copy(choice = choice.decrement())

  def down(): MenuModel = copy(choice = choice.increment())

  def select(): MenuModel = selected.action match {
    case sub: SubMenu =>
      copy(choice = MenuChoice(sub.options), history = history :+ choice)
    case other =>
      this
  }

  def back(): MenuModel = history match {
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
