package mood

import mood.scenes._
import org.scalatest.{Matchers, WordSpec}

class MenuModelSpec extends WordSpec with Matchers {

  "The Menu structure" should {

    "return an updated menu after navigation" in new MenuScope {
      val updated = menu.down().down()
      updated.selected.text shouldBe "three"
    }

    "return to the start after reaching the end of the menu" in new MenuScope {
      menu.down().down().down().selected.text shouldBe "one"
      menu.up().selected.text shouldBe "three"
    }

    "enter submenus" in new MenuScope {
      val updated = menu.down().select().down()
      updated.selected.text shouldBe "one-two"
    }

    "back out of submenus" in new MenuScope {
      val updated = menu.down().select().down().back()
      updated.selected.text shouldBe "two"
    }

    "do nothing when backing out of the main menu" in new MenuScope {
      menu.back() shouldBe menu
    }
  }

  trait MenuScope {
    object SceneOne extends GameScene.Key("scene-one")
    object SceneOnePointTwo extends GameScene.Key("scene-one-point-two")
    object SceneThree extends GameScene.Key("scene-three")

    val menu = MenuModel(
      MenuChoice(
        MenuOption("one", StartScene(SceneOne)),
        MenuOption("two", SubMenu(
          MenuOption("one-one", DoAction(() => Unit)),
          MenuOption("one-two", StartScene(SceneOnePointTwo))
        )),
        MenuOption("three", StartScene(SceneThree))
      )
    )
  }
}
