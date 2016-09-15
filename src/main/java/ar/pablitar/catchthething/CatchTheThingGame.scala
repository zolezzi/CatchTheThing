package ar.pablitar.catchthething

import com.uqbar.vainilla.DesktopGameLauncher
import scala.util.Random

object CatchTheThingGame extends App {
  
  val width = 800
  val height = 600
  
  val randomizer = new Random
  
  new DesktopGameLauncher(new CatchTheThing).launch()
}