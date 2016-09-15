package ar.pablitar.catchthething

import com.uqbar.vainilla.DeltaState

class PooSpawner extends Spawner{
  
  
  
  override def update(state: DeltaState) = {
    if(timer >= cooldown) {
      spawn
      timer = 0
      cooldown = generateCooldown + 0.5
    } else {
      timer += state.getDelta
    }
  }
  
  
  def spawn = {
    val poo = new Poo
    this.getScene.addComponent(poo)
  }

}