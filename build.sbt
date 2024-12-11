// Build and Scala version
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "3.6.1"

// Project definition
lazy val root = (project in file("."))
    .settings(
        name := "JP_Morgan_Chess",

        // Dependencies fjhgdoa
        libraryDependencies ++= Seq(
            "org.scalactic" %% "scalactic" % "3.2.14",
            "org.scalatest" %% "scalatest" % "3.2.14" % Test,

            "org.scalafx" %% "scalafx" % "20.0.0-R31"
        )
    )

/*val scala3Version = "3.5.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "othello",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.19",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "org.scalafx" %% "scalafx" % "16.0.0-R25"
    ),

    coverageExcludedPackages := ".*Othello.*" ,
  )
*/