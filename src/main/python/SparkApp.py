from pyspark import SparkContext

if __name__ == "__main__":
  sc = SparkContext()
  print "sc.appName = ", sc.appName
  sc.stop()
