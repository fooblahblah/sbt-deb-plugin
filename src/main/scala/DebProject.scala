import sbt._
import java.io.File
import org.vafer.jdeb.{Console => JDebConsole, DataProducer, Processor}
import org.vafer.jdeb.producers.{DataProducerFile, DataProducerDirectory}
import scala.Predef.{println => scalaPrintln}

trait DebProject extends DefaultProject {
  def debDestFile = new File(outputPath.absolutePath + File.separator + projectName.value + ".deb")

  def debControlDir = info.projectPath.asFile.getPath + File.separator + "debian"

  def debControlFiles = new File(debControlDir).listFiles

  def debContents = Array[File](jarPath.asFile)
  
  lazy val deb = debAction
  def debAction = debTask dependsOn(`package`)

  lazy val debTask = task { 
    val data = debContents.map { f => 
      if(f.isFile) 
        new DataProducerFile(f, null, null, null).asInstanceOf[DataProducer]
      else
        new DataProducerDirectory(f, null, null, null).asInstanceOf[DataProducer]                         
    }.toArray
    val processor = new Processor(new SbtConsole, null)
    val packageDescriptor = processor.createDeb(debControlFiles, data, debDestFile, "none")
    scalaPrintln("Create debian package in " + debDestFile)
    None 
  }
}

class SbtConsole extends JDebConsole {
  def println(s: String) { scalaPrintln(s) }
}
