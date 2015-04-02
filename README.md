<h3>AverageWordLength</h3>
<pre>
$ spark-submit --class my.spark.examples.scala.AverageWordLength target/my-spark-examples-1.0.jar gettysburg.txt
$ spark-submit src/main/python/AverageWordLength.py gettysburg.txt
</pre>

<h3>SparkApp</h3>
<pre>
$ spark-submit --class my.spark.examples.scala.SparkApp target/my-spark-examples-1.0.jar
$ spark-submit src/main/python/SparkApp.py
</pre>

<h3>WordCount</h3>
<pre>
$ spark-submit --class my.spark.examples.scala.WordCount target/my-spark-examples-1.0.jar gettysburg.txt
$ spark-submit src/main/python/WordCount.py gettysburg.txt
</pre>
