import sbt._

class DebPluginProject(info: ProjectInfo) extends PluginProject(info) {
  val jdeb = "org.vafer" % "jdeb" % "0.8" % "compile"
}
