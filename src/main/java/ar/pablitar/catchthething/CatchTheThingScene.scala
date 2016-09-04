package ar.pablitar.catchthething

import com.uqbar.vainilla.GameScene

class CatchTheThingScene extends GameScene {
  val catcher = new Catcher
  this.addComponent(catcher)
  this.addComponent(new BallSpawner)
}