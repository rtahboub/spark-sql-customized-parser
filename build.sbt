name := "LB2-playground"

version := "0.1"

resolvers += Resolver.mavenLocal

scalaVersion := "2.11.2"

scalaOrganization := "org.scala-lang.virtualized"

scalacOptions += "-language:postfixOps"

scalacOptions += "-language:implicitConversions"

scalacOptions += "-deprecation"

scalacOptions += "-DshowSuppressedErrors=true"

// tests are not thread safe
parallelExecution in Test := false

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.1"

libraryDependencies += "org.apache.spark" %% "spark-repl" % "2.2.1"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.2.1"

libraryDependencies += "org.apache.spark" %% "spark-catalyst" % "2.2.1"

libraryDependencies += "jline" % "jline" % "2.11"

// libraryDependencies += "com.github.melrief" %% "pureconfig" % "0.6.0"
libraryDependencies += "com.typesafe" % "config" % "1.3.1"

unmanagedJars in Compile <<= baseDirectory map { base => (base ** "*.jar").classpath }

// do not include repl scripts in assembly
sourcesInBase := false

