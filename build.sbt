name := """Warehouse"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters,
  "be.objectify" %% "deadbolt-java" % "2.5.3",
"com.feth" %% "play-authenticate" % "0.8.1"
)

