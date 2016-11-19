/**
  * Created by wanghongyi on 11/17/16.
  */
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating

object Als {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Spark Decision Tree").setMaster("local")
    val sc = new SparkContext(conf)

    // Load and parse the data file.
    val data = sc.textFile("/Users/wanghongyi/Desktop/Big Data project/project3/hw3datasetnew/ratings.dat")


    val ratings = data.map(_.split("::") match { case Array(user, item, rate, time) =>
      Rating(user.toInt, item.toInt, rate.toDouble)
    })

    // Split data into training (60%) and test (40%).
    val Array(training, test) = ratings.randomSplit(Array(0.6, 0.4))

    // Build the recommendation model using ALS
    val rank = 10
    val numIterations = 10
    val model = ALS.train(training, rank, numIterations, 0.01)

    // Evaluate the model on rating data
    val usersProducts = test.map { case Rating(user, product, rate) =>
      (user, product)
    }

    val predictions =
      model.predict(usersProducts).map { case Rating(user, product, rate) =>
        ((user, product), rate)
      }

    val ratesAndPreds = test.map { case Rating(user, product, rate) =>
      ((user, product), rate)
    }.join(predictions)

    println("training size: " + training.count())
    println("test size: " + test.count())
//    ratesAndPreds.foreach(r => println(Math.round(r._2._1) + " vs " + r._2._2.toLong))

    val testErr = ratesAndPreds.filter(r => r._2._1 != Math.round(r._2._2)).count().toDouble / ratesAndPreds.count()
    println("Accuracy = " + (1.0 - testErr))

    val MSE = ratesAndPreds.map { case ((user, product), (r1, r2)) =>
      val err = (r1 - r2)
      err * err
    }.mean()

    println("Mean Squared Error = " + MSE)

  }
}



