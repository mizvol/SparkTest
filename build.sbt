name := "SparkTest"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.10" % "1.6.1" exclude ("org.apache.hadoop","hadoop-yarn-server-web-proxy")
)