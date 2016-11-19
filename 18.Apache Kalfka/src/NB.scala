import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.util.MLUtils
/**
  * Created by wanghongyi on 11/17/16.
  */
object NB {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Spark Decision Tree").setMaster("local")
    val sc = new SparkContext(conf)

    // Load and parse the data file.
    val data = sc.textFile("/Users/wanghongyi/Desktop/Big Data project/project3/hw3datasetnew/glass.data")
    val parsedData = data.map { line =>
      val nums = line.split(",")
      LabeledPoint(nums(10).toInt, Vectors.dense(nums(0).toDouble, nums(1).toDouble, nums(2).toDouble,
        nums(3).toDouble, nums(4).toDouble, nums(5).toDouble, nums(6).toDouble, nums(7).toDouble
        , nums(8).toDouble, nums(9).toDouble))
    }

    // Split data into training (60%) and test (40%).
    val Array(training, test) = parsedData.randomSplit(Array(0.6, 0.4))

    val model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")

    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()

    println("Accuracy = " + accuracy)
  }
}


