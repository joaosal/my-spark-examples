package my.spark.examples

import org.apache.spark.SparkContext

object SparkApp {
  def main(args: Array[String]) {
    val sc = new SparkContext()
    println( "sc.appName = " + sc.appName)
    sc.stop()
  }
}
