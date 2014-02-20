name := "kafka-stream-demo"

scalaVersion := "2.9.2"

resolvers += "Apache repo" at "https://repository.apache.org/content/repositories/releases"

libraryDependencies += "org.apache.kafka" % "kafka_2.9.2" % "0.8.0" exclude("com.sun.jmx","jmxri") exclude("com.sun.jdmk","jmxtools")
