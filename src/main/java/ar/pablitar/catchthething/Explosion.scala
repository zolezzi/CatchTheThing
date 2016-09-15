package ar.pablitar.catchthething

import com.uqbar.vainilla.DeltaState
import ar.pablitar.vainilla.commons.components.SpeedyComponent
import ar.pablitar.vainilla.commons.math.Vector2D

class Explosion extends SpeedyComponent[CatchTheThingScene] {
  //this.speed = component.speed * 0.1

  this.setAppearance(Resources.spriteExplosion)

 
  val duration = 0.3
  var current = 0.0

  override def update(state: DeltaState): Unit = {
    if (current >= duration) {
      this.destroy
    } else {
      applySpeed(state)
      current += state.getDelta
    }
  }
}