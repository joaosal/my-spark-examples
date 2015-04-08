package my.spark.examples.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._

object ReqCount {

  def main(args: Array[String]) {
  
    var host:String = ""
    var port:Int = 0
    var mode:String = ""

    val batchDuration = 2
    val slideDuration = 4
    val windowDuration = 6

    if (args.length == 2) {
      host = args(0)
      port = args(1).toInt
      mode = "batch"
    } else if (args.length == 3 && (args(2) == "state" || args(2) == "window")) {
      host = args(0)
      port = args(1).toInt
      mode = args(2)
    } else {
      println("Usage: ReqCount <host> <port> [state|window]")
      System.exit(1)
    }

    if (mode == "batch" || mode == "state") {
      println("\n" + "batchDuration: " + batchDuration + "\n")
    } else {
      println("\n" + "batchDuration: " + batchDuration + ", slideDuration: " + slideDuration + ", windowDuration: " + windowDuration + "\n")
    }
    
    val ssc = new StreamingContext(new SparkConf(), Seconds(batchDuration))

    if (mode == "state" || mode == "window") {
      ssc.checkpoint("checkpoints")
    }

    val logs = ssc.socketTextStream(host, port)
    val kblogs = logs.filter(log => log.contains("KBDOC"))
    val batchCount = kblogs.count().map(num => num.toInt)

    batchCount.foreachRDD((rdd, time) => {
      println("Time: " + time + ". # Logs in Batch:  " + rdd.collect()(0))
    })

    if (mode == "state") {
      val pair = batchCount.map(count => ("count", count))
      val state = pair.updateStateByKey(updateCount)

      state.foreachRDD((rdd, time) => {
        println("Time: " + time + ". # Logs total:     " + rdd.collect()(0)._2 + "\n")
      })
    }
    
    else if (mode == "window") {
      val counts = kblogs.countByWindow(Seconds(windowDuration), Seconds(slideDuration)).map(n => n.toInt)
      val pairs = counts.map(n => ("count", n))
      val total = pairs.reduceByKey((v1, v2) => v1 + v2)

      total.foreachRDD((rdd, time) => {
        println("Time: " + time + ". # Logs in Window: " + rdd.collect()(0)._2 + "\n")
      })
    }

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
