import sys
from pyspark import SparkContext

if __name__ == "__main__":
  if len(sys.argv) != 3:
    print "Usage: IpAddrCount <file or directory> <ipAddr>"
    exit(0)
    
  logs = sys.argv[1]
  ipAddr = sys.argv[2]

  sc = SparkContext()
  count = sc.textFile(logs).filter(lambda line: ipAddr in line).count()
  print( "The Ip Address " + ipAddr + " appears " + str(count) + " times in the logs.")
  sc.stop()
