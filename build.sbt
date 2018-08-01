name := "twitter-monitor"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.3.1",
  "org.apache.spark" % "spark-streaming_2.11" % "2.3.1",
  "org.apache.spark" % "spark-streaming-twitter_2.11" % "1.6.3",
  "org.twitter4j" % "twitter4j-core" % "4.0.6",
  "org.twitter4j" % "twitter4j-stream" % "4.0.6",

  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)