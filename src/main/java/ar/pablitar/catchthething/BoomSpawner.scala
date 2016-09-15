package ar.pablitar.catchthething


import com.uqbar.vainilla.DeltaState

class BoomSpawner extends Spawner{
  
  
  
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
    val boom = new Boom
    this.getScene.addComponent(boom)
  }
}