import de.johoop.jacoco4sbt._
import JacocoPlugin._

jacoco.settings

name := "aksap-bibtex"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache
)     

play.Project.playJavaSettings
