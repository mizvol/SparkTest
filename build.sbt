name := "SparkTest"

version := "1.0"

scalaVersion := "2.10.4"

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "2.0.0" exclude ("org.apache.hadoop","hadoop-yarn-server-web-proxy"),
  "graphframes" % "graphframes" % "0.2.0-spark2.0-s_2.10",
  "org.apache.spark" % "spark-sql_2.10" % "2.0.0",
  "org.apache.spark" % "spark-graphx_2.10" % "2.0.0"
)