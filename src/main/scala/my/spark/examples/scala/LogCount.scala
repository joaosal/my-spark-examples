package my.spark.examples.scala

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object LogCount {
  def main(args: Array[String]) {

    if (args.length < 1) {
      System.err.println("Usage: LogCount <directory|file>")
      System.exit(1)
    }
    val sc = new SparkContext()
    val count = sc.textFile(args(0)).count()
    println( "Log Count: " + count)
    sc.stop()
  }
}
