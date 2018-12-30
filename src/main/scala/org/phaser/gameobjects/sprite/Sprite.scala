package org.phaser.gameobjects.sprite

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.Origin

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.Sprite")
class Sprite extends GameObject with Origin
