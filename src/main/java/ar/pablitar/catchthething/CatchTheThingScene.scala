package ar.pablitar.catchthething


import com.uqbar.vainilla.GameScene
import com.uqbar.vainilla.GameComponent
import ar.pablitar.vainilla.commons.math.Vector2D
import java.awt.Graphics2D

class CatchTheThingScene extends GameScene {
  val catcher = new Catcher(new CatcherShadow)
  
  val background = new GameComponent
  background.setAppearance(Resources.background)
  background.setZ(-50)
  
  val scoreDisplay = new ScoreDisplay(this)
  
  val spawnerPoo = new PooSpawner
  val spawnerBoom = new BoomSpawner
  val spawnerBall = new BallSpawner
  
  this.addComponent(spawnerPoo)
  this.addComponent(scoreDisplay)
  this.addComponent(background)
  this.addComponent(catcher)
  this.addComponent(catcher.shadow)
  this.addComponent(spawnerBall)
  this.addComponent(spawnerBoom)
  this.addComponent(new Sun(Vector2D(575, 85)))
  
  var isRestartOnEnd = false
  
  def restartOnEnd = {
    isRestartOnEnd = true
  }
  
   override def takeStep(graphics: Graphics2D) = {
    super.takeStep(graphics)

    if (isRestartOnEnd) restart
  }
  
  def gameOver = {
    this.addComponent(new GameOverComponent(this));
    this.removeComponent(spawnerPoo)
    this.removeComponent(spawnerBoom)
    this.removeComponent(spawnerBall)
  }

  def restart = {
    this.getGame.setCurrentScene(new CatchTheThingScene)
  }
  
  def getScore = {
    scoreDisplay.getScore
  }
  
   def sumScore = {
    scoreDisplay.sumScore
  }
  
    def subScore = {
    scoreDisplay.subScore
  }
}