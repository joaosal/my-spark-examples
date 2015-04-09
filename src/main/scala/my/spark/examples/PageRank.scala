package my.spark.examples

import org.apache.spark.SparkContext

object PageRank {
  def main(args: Array[String]) {
    if (args.length != 0) {
      println("Usage: PageRank")
      System.exit(1)
    }
    val sc = new SparkContext()
    println( "sc.appName = " + sc.appName)
    sc.stop()
  }
}
