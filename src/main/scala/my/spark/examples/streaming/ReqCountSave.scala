package my.spark.examples.streaming

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._

object ReqCountSave {
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Usage: ReqCountSave <host> <port>")
      System.exit(1)
    }  
    
    val hostname = args(0)
    val port = args(1).toInt
    val batchDuration = 2

    val ssc = new StreamingContext(new SparkConf(), Seconds(batchDuration))
    val userIdReqCount = ssc.socketTextStream(hostname, port).map(line => (line.split(' ')(2), 1)).reduceByKey(_+_)
    
    userIdReqCount.print()
    userIdReqCount.saveAsTextFiles("reqcount/slice")

    ssc.start()
    ssc.awaitTermination()
  }
}
