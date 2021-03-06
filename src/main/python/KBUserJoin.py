import sys
import re
from pyspark import SparkContext

if __name__ == "__main__":
  
  kblist = "kblist.txt"
  weblogs = "weblogs"

  sc = SparkContext()
  
  def getRequestDoc(s):
    match=re.search(r'KBDOC-[0-9]*',s)
    return match.group() if match else None

  docs = sc.textFile(kblist).map(lambda line: line.split(':')).map(lambda tokens:(tokens[0],tokens[1])).cache()
  print("\n*** Document Id & Title")
  for doc in docs.take(5): print '({0}, {1})'.format(doc[0], doc[1])

  logs = sc.textFile(weblogs).map(lambda line:(getRequestDoc(line),line.split(' ')[2])).filter(lambda (docId,userId): docId is not None).distinct()
  print("\n*** DocId and UserId")
  for log in logs.take(5): print '({0}, {1})'.format(log[0], log[1])

  userDocs = logs.join(docs).map(lambda (docId,(userId,title)):(userId,title)).groupByKey()
  print("\n*** UserId and List of titles")
  for (userId,titles) in userDocs.take(3):
    print "userid: ", userId
    for title in titles: print '\t',title
  print
  sc.stop()