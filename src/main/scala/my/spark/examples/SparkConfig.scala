package my.spark.examples

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object SparkConfig {
  def main(args: Array[String]) {

    if (args.length < 1 || (args(0) != "compileTime" && args(0) != "runTime") ) {
      System.err.println("Usage: SparkConfig <compileTime|runTime>")
      System.exit(1)
    }

    if (args(0) == "compileTime") {
      val conf = new SparkConf().setAppName("CT App")
      val sc = new SparkContext(conf)
      println("Application Name: " + sc.appName)
      sc.stop()
    }
    
    else {
      val sc = new SparkContext()
      println("Application Name: " + sc.appName)
      sc.stop()
    }
  }
}
