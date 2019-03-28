package org.phaser.gameobjects.particles

import scala.scalajs.js

@js.native
class ParticleEmitterManager extends js.Object {

  def emitters: js.Array[ParticleEmitter] = js.native

  def createEmitter(config: ParticleEmitterConfig): ParticleEmitter = js.native

  def emitParticleAt(x: Double, y: Double): Particle = js.native

}
