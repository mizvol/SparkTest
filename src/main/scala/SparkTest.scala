import org.apache.spark.sql.SparkSession
import org.miz.helper.Helper._

object SparkWordCount {
  def main(args: Array[String]): Unit = {

    suppressLogs(List("org", "akka"))

    val spark = SparkSession.builder
      .master("local")
      .appName("Spark Graph Frames")
      .getOrCreate()

    val lines = spark.sparkContext.parallelize(Seq("This is the first line", "This is the second line", "This is the third line"))

    val counts = lines.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    counts.foreach(println)
  }

}