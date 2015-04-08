package my.spark.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

object IpAddrCount {
  def main(args: Array[String]) {

    if (args.length != 2) {
      System.err.println("Usage: IpAddrCount <directory|file> <ipAddr>")
      System.exit(1)
    }
    
    val logs = args(0)
    val ipAddr = args(1)

    val sc = new SparkContext()
    val count = sc.textFile(logs).filter(line => line.contains(ipAddr)).count()
    println( "The Ip Address " + ipAddr + " appears " + count + " times in the logs.")
    sc.stop()
  }
}
