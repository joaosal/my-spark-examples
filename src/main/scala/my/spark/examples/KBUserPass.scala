package my.spark.examples

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import scala.io.Source

object KBUserPass {

  def getRequestDocId(s: String): String = { 
    val matchre = "KBDOC-[0-9]*".r
    matchre.findFirstIn(s).orNull
  }

  def main(args: Array[String]) {
  
    val kblist = "/home/training/training_materials/sparkdev/data/kblist.txt"
    val weblogs = "weblogs"

    val sc = new SparkContext()

    val docs = Source.fromFile(kblist).getLines.map(_.split(":")).map(tokens => (tokens(0),tokens(1))).toMap
    println("\n*** Document Id & Title")
    docs.take(5).foreach(println)

    val logs = sc.textFile(weblogs).map(line => (getRequestDocId(line),line.split(" ")(2))).filter(pair => pair._1 != null).distinct().cache()
    println("\n*** DocId and UserId")
    logs.take(5).foreach(println)

    val userDocs = logs.map{case (docId,userId) => (userId,docs(docId))}.groupByKey()
    println("\n*** UserId and List of titles")
    for ((userId,titles) <- userDocs.take(3)) {
      println("userid: " + userId)
      for (title <- titles) println ("\t" + title)
    }
    println()
    sc.stop()
  }
}
