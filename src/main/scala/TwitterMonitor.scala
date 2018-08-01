import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.io.Source

object TwitterMonitor {
  import TwitterHelper._
  def main(args: Array[String]): Unit = {

    readTwitterTokens(Source.fromFile("twitter.keys"))
      .foreach(setToken)

    val sc = new StreamingContext("local[*]", "TwitterMonitor", Seconds(1))
    val tweets = TwitterUtils.createStream(sc, None)

    val lastHourHashTagCounts = tweets
      .map(_.getText)
      .flatMap(parseWords)
      .filter(getHashTaggedWords)
      .map(addCount)
      .reduceByKeyAndWindow(_ + _, _ - _, Seconds(hourInSeconds), Seconds(1))
      .transform(_.sortBy(_._2, ascending = false))

    lastHourHashTagCounts.print

    sc.checkpoint("C:/tmp")
    sc.start()
    sc.awaitTermination()
  }

}

object TwitterHelper {
  val hourInSeconds: Int = 60 * 60

  def readTwitterTokens(file: Source): Map[String, String] =
    (for {
      line <- file.getLines()
      words = line.split("\\s+").toList
    } yield {
      words.head -> words.tail.head
    }).toMap

  def setToken(entry: (String, String)): String = entry match {
    case (k, v) => System.setProperty(k, v)
  }

  def parseWords(s: String): List[String] = s.split("\\s+").toList
  def getHashTaggedWords(s: String): Boolean = s.startsWith("#")
  def addCount(s: String): (String, Int) = (s, 1)
}
