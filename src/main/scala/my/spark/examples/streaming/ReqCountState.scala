package my.spark.examples.streaming

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._

object ReqCountState {
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Usage: ReqCountState <host> <port>")
      System.exit(1)
    }  
    
    val hostname = args(0)
    val port = args(1).toInt
    val batchDuration = 2

    val ssc = new StreamingContext(new SparkConf(), Seconds(batchDuration))
    val userIdReqCount = ssc.socketTextStream(hostname, port).map(line => (line.split(' ')(2), 1)).reduceByKey(_+_)

    ssc.checkpoint("checkpoints")

    val totals = userIdReqCount.updateStateByKey(updateCount).map(_.swap).transform(rdd => rdd.sortByKey(false))

    totals.foreachRDD((rdd, time) => {
      println("*** Top IP Addresses from the beginning until " + time)
      rdd.take(5).foreach { case (count, ip) => printf("IP: %s (%s)\n", ip, count) }
    })

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }

  def updateCount(newCounts: Seq[Int], state: Option[Int]): Option[Int] = {
    val newCount = newCounts.foldLeft(0)(_ + _)
    val previousCount = state.getOrElse(0)
    Some(newCount + previousCount)
  }
}
