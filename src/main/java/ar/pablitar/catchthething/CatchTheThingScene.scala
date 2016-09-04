package ar.pablitar.catchthething

import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.GameComponent
import ar.pablitar.vainilla.commons.math.Vector2D

class CatchTheThingScene extends GameScene {
  val catcher = new Catcher(new CatcherShadow)
  
  val background = new GameComponent
  background.setAppearance(Resources.background)
  background.setZ(-50)
  
  this.addComponent(background)
  this.addComponent(catcher)
  this.addComponent(catcher.shadow)
  this.addComponent(new BallSpawner)
  this.addComponent(new Sun(Vector2D(575, 85)))
}