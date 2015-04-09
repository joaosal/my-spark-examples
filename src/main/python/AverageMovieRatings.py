import sys
from pyspark import SparkContext

if __name__ == "__main__":

  sc = SparkContext()
  
  movies =  sc.textFile("hdfs://localhost/user/training/movie/part*")\
    .map(lambda line: line.split("\t"))\
    .map(lambda tokens: (tokens[0],tokens[1]))

  ratings = sc.textFile("hdfs://localhost/user/training/movierating/part*")\
    .map(lambda line: line.split("\t"))\
    .map(lambda tokens: (tokens[1],int(tokens[2])))\
    .groupByKey()\
    .mapValues(lambda ratings: sum(ratings)/float(len(ratings)))

  namesRatings = movies.join(ratings)\
    .map(lambda (id,(title,rating)):(int(id),(title,rating)))\
    .sortByKey(True)

  for (id,(title,rating)) in namesRatings.take(10): print("(" + str(id) + "(" + title + "," + str(rating) + "))")
  
  sc.stop()