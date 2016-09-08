package ar.pablitar.catchthething

import com.uqbar.vainilla.GameComponent
import com.uqbar.vainilla.DeltaState
import scala.util.Random

class BallSpawner extends GameComponent[CatchTheThingScene] {
  
  var cooldown = generateCooldown
  var timer = 0.0
  
  //Hasta 60 veces por segundo
  override def update(state: DeltaState) = {
    if(timer >= cooldown) {
      spawnBall
      timer = 0
      cooldown = generateCooldown
    } else {
      timer += state.getDelta
    }
  }
  
  def spawnBall = {
    val ball = new Ball
    this.getScene.addComponent(ball)
  }

  def generateCooldown = {
    (Random.nextDouble() * 0.5) + 0.2
  }
}



