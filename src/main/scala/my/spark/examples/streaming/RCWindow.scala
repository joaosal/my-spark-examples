package my.spark.examples.streaming

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._

object RCWindow {
  def main(args: Array[String]) {
    if (args.length < 6) {
      System.err.println("Usage: RCWindow <host> <port> <batchDuration> <slideDuration> <windowDuration> <filter>")
      System.exit(1)
    }  

    val hostname = args(0)
    val port = args(1).toInt
    val batchDuration = args(2).toInt
    val slideDuration = args(3).toInt
    val windowDuration = args(4).toInt
    val filter = args(5)
    
    println("\n" + "batchDuration: " + batchDuration + ", slideDuration: " + slideDuration + ", windowDuration: " + windowDuration + "\n")

    val ssc = new StreamingContext(new SparkConf(), Seconds(batchDuration))
    val logs = ssc.socketTextStream(hostname, port)
    val flogs = logs.filter(_.contains(filter))
    val batch = flogs.count().map(_.toInt)

    batch.foreachRDD((rdd, time) => {
      println("Time: " + time + ", Logs in Batch:  " + rdd.collect()(0))
    })

    ssc.checkpoint("checkpoints")

    val counts = flogs.countByWindow(Seconds(windowDuration), Seconds(slideDuration)).map(_.toInt)
    val pairs = counts.map(n => ("count", n))
    val window = pairs.reduceByKey(_+_)
    
    window.foreachRDD((rdd, time) => {
      println("Time: " + time + ". Logs in Window: " + rdd.collect()(0)._2 + "\n")
    })

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
