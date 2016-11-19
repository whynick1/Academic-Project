/**
  * Created by wanghongyi on 11/17/16.
  */
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.regression.LabeledPoint

object DT {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Spark Decision Tree").setMaster("local")
    val sc = new SparkContext(conf)

    // Load and parse the data file.
    val data = sc.textFile("/Users/wanghongyi/Desktop/Big Data project/project3/hw3datasetnew/glass.data")
    val parsedData = data.map { line =>
      val nums = line.split(",")
      LabeledPoint(nums(10).toDouble, Vectors.dense(line(1).toDouble, line(2).toDouble,
        line(3).toDouble, line(4).toDouble, line(5).toDouble, line(6).toDouble, line(7).toDouble
      , line(8).toDouble, line(9).toDouble))
    }

    // Split the data into training and test sets (30% held out for testing)
    val splits = parsedData.randomSplit(Array(0.6, 0.4))
    val (trainingData, testData) = (splits(0), splits(1))

    // Train a DecisionTree model.
    //  Empty categoricalFeaturesInfo indicates all features are continuous.
    val numClasses = 8
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "gini"
    val maxDepth = 5
    val maxBins = 32

    val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      impurity, maxDepth, maxBins)

    // Evaluate model on test instances and compute test error
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }

    val testErr = labelAndPreds.filter(r => r._1 != r._2).count().toDouble / testData.count()
    println("Test Error = " + testErr)
    println("Accuracy = " + (1.0 - testErr))
    println("Learned classification tree model:\n" + model.toDebugString)

  }
}
