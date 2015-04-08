<h2>Data</h2>

<h3>casey.txt</h3>

<p><a href="https://github.com/hagenhaus/my-spark-examples/blob/master/data/casey.txt">casey.txt</a></p>

<h3>epa-http.txt</h3>

<p><a href="http://ita.ee.lbl.gov/html/contrib/EPA-HTTP.html">EPA-HTTP</a></p>

<h3>gettysburg.txt</h3>

<p><a href="https://github.com/hagenhaus/my-spark-examples/blob/master/data/gettysburg.txt">gettysburg.txt</a></p>

<h3>GlobalAirportDatabase</h3>

<p><a href="http://www.partow.net/miscellaneous/airportdatabase/">Global Airport Database</a></p>

<h2>Examples</h2>

<h3>AverageWordLength</h3>
<pre>
$ spark-submit --class my.spark.examples.AverageWordLength target/my-spark-examples-1.0.jar gettysburg.txt
$ spark-submit src/main/python/AverageWordLength.py gettysburg.txt
</pre>

<h3>IpAddrCount</h3>
<pre>
$ spark-submit --class my.spark.examples.IpAddrCount target/my-spark-examples-1.0.jar access.log 175.44.24.82
$ spark-submit src/main/python/IpAddrCount.py access.log 175.44.24.82
</pre>

<h3>LineCount</h3>
<pre>
$ spark-submit --class my.spark.examples.LineCount target/my-spark-examples-1.0.jar casey.txt
$ spark-submit --class my.spark.examples.LineCount target/my-spark-examples-1.0.jar access.log

$ spark-submit src/main/python/LineCount.py casey.txt
$ spark-submit src/main/python/LineCount.py access.log
</pre>

<h3 id="sparkapp">SparkApp</h3>
<pre>
$ spark-submit --class my.spark.examples.SparkApp target/my-spark-examples-1.0.jar
$ spark-submit src/main/python/SparkApp.py
</pre>

<h3 id="sparkconfig">SparkConfig</h3>
<pre>
$ spark-submit --class my.spark.examples.SparkConfig target/my-spark-examples-1.0.jar compileTime
$ spark-submit --class my.spark.examples.SparkConfig --name "RT App" target/my-spark-examples-1.0.jar runTime

$ spark-submit src/main/python/SparkConfig.py compileTime
$ spark-submit --name "RT App" src/main/python/SparkConfig.py runTime
</pre>

<h3>WordCount</h3>
<pre>
$ spark-submit --class my.spark.examples.WordCount target/my-spark-examples-1.0.jar gettysburg.txt
$ spark-submit src/main/python/WordCount.py gettysburg.txt
</pre>

<h2>Streaming Examples</h2>
<h3>NetCatcher</h3>
<pre>
Terminal 1:
$ nc -lk 1234

Terminal 2:
$ spark-submit --class my.spark.examples.streaming.NetCatcher --master local[2] target/my-spark-examples-1.0.jar localhost 1234
</pre>
