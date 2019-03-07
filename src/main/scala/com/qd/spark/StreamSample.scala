package com.qd.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming._
object Test
{

  
  
  def main(args : Array[String]) {
    
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(1))
    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)

    // 将该DStream产生的RDD的头十个元素打印到控制台上
    wordCounts.print()
    ssc.start();
    ssc.awaitTermination()
  }
  
}