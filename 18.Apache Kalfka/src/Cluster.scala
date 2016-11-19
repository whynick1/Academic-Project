/**
  * Created by wanghongyi on 11/16/16.
  */
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.clustering.{KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors

object Cluster {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Spark Cluster").setMaster("local")
    val sc = new SparkContext(conf)

    // Load and parse the data
    val data = sc.textFile("/Users/wanghongyi/Desktop/Big Data project/project3/hw3datasetnew/itemusermat")
    val parsedData = data.map(s => Vectors.dense(s.split("\\s+").drop(1).map(_.toDouble))).cache()

    // Cluster the data into two classes using KMeans
    val numClusters = 10
    val numIterations = 20
    val clusters = KMeans.train(parsedData, numClusters, numIterations)

    // get prediction vectors for all movies, and then get their predictions <Movie_id, Prediction>
    val predictions = data.map { line =>
      val nums = line.split("\\s+")
      (nums(0), clusters.predict(Vectors.dense(nums.tail.map(_.toDouble))))
    }

    //val movie = sc.textFile("/hw3spring/movies.dat")
    val movie = sc.textFile("/Users/wanghongyi/Desktop/Big Data project/project3/hw3datasetnew/movies.dat")
    val moviesData = movie.map { line =>
      val info = line.split("::")
      (info(0), (info(1) + "," + info(2))) //rdd tuple
    }

    //join movieData with prediction vecort
    val joinResult = predictions.join(moviesData).map(p => (p._2._1, (p._1, p._2._2)))

    //take 5 movie for each clusters
    val groupResult = joinResult.groupByKey().sortByKey().map(p => (p._1, p._2.take(5))).collect()

    //print result
    groupResult.foreach(p => println("cluster:" + p._1 + "\n" + p._2.mkString("\n")))
  }
}
