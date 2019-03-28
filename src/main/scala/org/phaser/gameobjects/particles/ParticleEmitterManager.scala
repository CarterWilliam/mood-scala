package org.phaser.gameobjects.particles

import org.phaser.gameobjects.GameObject
import org.phaser.gameobjects.components.{Depth, Transform}

import scala.scalajs.js

@js.native
@js.annotation.JSGlobal("Phaser.GameObjects.Particles.ParticleEmitterManager")
class ParticleEmitterManager extends GameObject
  with Depth[ParticleEmitterManager]
  with Transform[ParticleEmitterManager] {

  def emitters: js.Array[ParticleEmitter] = js.native

  def createEmitter(config: ParticleEmitterConfig): ParticleEmitter = js.native

  def emitParticleAt(x: Double, y: Double): Particle = js.native

}
