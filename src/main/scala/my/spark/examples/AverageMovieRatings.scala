package my.spark.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object AverageMovieRatings {
  def main(args: Array[String]) {

    val sc = new SparkContext()
  
    val movies =  sc.textFile("hdfs://localhost/user/training/movie/part*")
      .map(_.split("\t"))
      .map(tokens => (tokens(0),tokens(1)))

    val ratings = sc.textFile("hdfs://localhost/user/training/movierating/part*")
      .map(_.split("\t"))
      .map(tokens => (tokens(1),tokens(2).toInt))
      .groupByKey()
      .mapValues(ratings => ratings.sum/ratings.size.toFloat)

    val namesRatings = movies.join(ratings)
      .map{case(id,(title,rating)) => (id.toInt,(title,rating))}
      .sortByKey(true)

    namesRatings.take(10).foreach(println)
  
    sc.stop()
  }
}
