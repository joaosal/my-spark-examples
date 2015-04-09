package my.spark.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object PageRank {
  def main(args: Array[String]) {
    val sc = new SparkContext()

    val links = sc.textFile("links.txt").map(_.split(" ")).map(pages => (pages(0),pages(1))).distinct().groupByKey().cache()
    println("\n(Source, (Target List))")
    links.collect().foreach { case (page,list) => println("(" + page + ",(" + list.mkString(", ") + "))") }

    var ranks = links.map(pair => (pair._1, 1.0))
    println("\n(Page, InitialRank)")
    ranks.collect().foreach(println)

    for (x <- 1 to 10) {
      var contribs = links.join(ranks).flatMap(pair => computeContributions(pair._2._1, pair._2._2)) 
      ranks = contribs.reduceByKey(_+_).map { case (page,rank) => (page, rank * 0.85 + 0.15) }
      println("\nIteration " + x)
      for (rankpair <- ranks.collect()) println(rankpair)    
    }
    println()
    sc.stop()
  }

  def computeContributions(neighbors: Iterable[String], rank: Double): Iterable[(String,Double)] = {
    for (neighbor <- neighbors) yield(neighbor, rank/neighbors.size)
  }
}