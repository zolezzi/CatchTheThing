package ar.pablitar.catchthething

import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.DeltaState
import scala.util.Random

trait Spawner extends GameComponent[CatchTheThingScene] {
  
  var cooldown = generateCooldown
  var timer = 0.0
  

  override def update(state: DeltaState)
  
  def spawn

  def generateCooldown = {
    (Random.nextDouble() * 0.5) + 0.2
  }
}