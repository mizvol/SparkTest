import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.graphframes._
import org.graphframes.GraphFrame
import org.apache.spark.sql.SparkSession

object SparkGraphFrames {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .master("local")
      .appName("Spark Graph Frames")
      .getOrCreate()

    val v = spark.createDataFrame(List(
      ("a", "Alice", 34),
      ("b", "Bob", 36),
      ("c", "Charlie", 30)
    )).toDF("id", "name", "age")
    // Create an Edge DataFrame with "src" and "dst" columns
    val e = spark.createDataFrame(List(
      ("a", "b", "friend"),
      ("b", "c", "follow"),
      ("c", "b", "follow")
    )).toDF("src", "dst", "relationship")
    // Create a GraphFrame

    val g = GraphFrame(v, e)

    // Query: Get in-degree of each vertex.
    g.inDegrees.show()

    // Query: Count the number of "follow" connections in the graph.
    g.edges.filter("relationship = 'follow'").count()

    // Run PageRank algorithm, and show results.
    val results = g.pageRank.resetProbability(0.01).maxIter(20).run()
    results.vertices.select("id", "pagerank").show()
  }
}

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