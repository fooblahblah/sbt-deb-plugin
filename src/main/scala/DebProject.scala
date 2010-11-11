import sbt._

trait DebProject extends Project {
  lazy val deb = task { log.info("Creating deb"); None }
}
