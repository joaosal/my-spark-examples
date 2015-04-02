import sys
from pyspark import SparkContext

if __name__ == "__main__":
  if len(sys.argv) < 2:
    print >> sys.stderr, "Usage: LogCount <directory|file>"
    exit(-1)
  sc = SparkContext()
  count = sc.textFile(sys.argv[1]).count()
  print "Log Count: ", count
