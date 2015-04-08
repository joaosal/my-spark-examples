package my.spark.examples

import org.apache.spark.SparkContext

object LineCount {
  def main(args: Array[String]) {

    if (args.length < 1) {
      System.err.println("Usage: LineCount <directory|file>")
      System.exit(1)
    }
    val sc = new SparkContext()
    val count = sc.textFile(args(0)).count()
    println( "Line Count: " + count)
    sc.stop()
  }
}
