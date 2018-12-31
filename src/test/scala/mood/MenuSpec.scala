package mood

import mood.scenes._
import org.scalatest.{Matchers, WordSpec}

class MenuSpec extends WordSpec with Matchers {

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

    "select actions" in new MenuScope {
      menu.down().select().select()
      mutable shouldBe "one-one"
    }
  }

  trait MenuScope {
    var mutable: String = "untouched"
    val menu = Menu(
      MenuChoice(
        MenuOption("one", StartScene("scene-one")),
        MenuOption("two", SubMenu(
          MenuOption("one-one", DoAction(() => mutable = "one-one")),
          MenuOption("one-two", StartScene("scene-one-point-two"))
        )),
        MenuOption("three", StartScene("scene-three"))
      )
    )
  }
}
