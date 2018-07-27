resolvers ++= Seq(
  "Typesafe Repository @ Plugins" at "https://repo.typesafe.com/typesafe/releases/",
  "Artima Maven Repository @ Plugins" at "http://repo.artima.com/releases/"
)
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.7")
addSbtPlugin("com.codacy" % "sbt-codacy-coverage" % "1.3.14")
