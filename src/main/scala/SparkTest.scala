import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.Logger
import org.apache.log4j.Level

object SparkWordCount {
  def main(args: Array[String]): Unit = {

    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val conf = new SparkConf()
      .setAppName("SparkWordCount")
      .setMaster("local[*]")
      .set("spark.executor.memory", "2g")

    val sc = new SparkContext(conf)

    val lines = sc.parallelize(Seq("This is the first line", "This is the second line", "This is the third line"))

    val counts = lines.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)

    counts.foreach(println)
  }
  
}