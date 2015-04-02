package my.spark.examples.scala

import org.apache.spark.SparkContext

object SparkApp {
  def main(args: Array[String]) {
    val sc = new SparkContext()
    println( "sc.appName = " + sc.appName)
    sc.stop()
  }
}
