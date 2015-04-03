import sys
from pyspark import SparkConf
from pyspark import SparkContext

if __name__ == "__main__":
  if len(sys.argv) < 2 or ( sys.argv[1] != "compileTime" and sys.argv[1] != "runTime" ):
    print >> sys.stderr, "Usage: SparkConfig <compileTime|runTime>"
    exit(-1)

  if sys.argv[1] == "compileTime":
    conf = SparkConf().setAppName("CT App")
    sc = SparkContext(conf=conf)
    print("Application Name: " + sc.appName)
    sc.stop()

  else:
    sc = SparkContext()
    print("Application Name: " + sc.appName)
    sc.stop()
