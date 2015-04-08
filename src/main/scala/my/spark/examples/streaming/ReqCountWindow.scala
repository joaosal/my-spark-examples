package my.spark.examples.streaming

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._

object ReqCountWindow {
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Usage: ReqCountWindow <host> <port>")
      System.exit(1)
    }  
    
    val hostname = args(0)
    val port = args(1).toInt
    val batchDuration = 2
    val slideDuration = 2
    val windowDuration = 6

    val ssc = new StreamingContext(new SparkConf(), Seconds(batchDuration))
    val userIdReqCount = (ssc
      .socketTextStream(hostname, port)
      .map(line => (line.split(' ')(2), 1))
      .reduceByKeyAndWindow((v1:Int,v2:Int) => v1+v2, Seconds(windowDuration), Seconds(slideDuration)))
    val reqCountUserId = userIdReqCount.map(_.swap).transform(rdd => rdd.sortByKey(false))

    reqCountUserId.foreachRDD( rdd => {
      println("*** Top users in window:")
      rdd.take(5).foreach { case (count, id) => printf("User: %s (%s)\n", id, count) }
    })

    ssc.start()
    ssc.awaitTermination()
  }
}
