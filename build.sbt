enablePlugins(JmhPlugin)

name := "scalasql"
organization := "objektwerks"
version := "5.0.0"
scalaVersion := "3.8.0-RC3"
libraryDependencies ++= {
  Seq(
    "com.lihaoyi" %% "scalasql-namedtuples" % "0.2.3",
    "com.h2database" % "h2" % "2.4.240",
    "com.typesafe" % "config" % "1.4.3",
    "ch.qos.logback" % "logback-classic" % "1.5.21",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all"
)
