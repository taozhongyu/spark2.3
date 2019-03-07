package com.qd.spark

import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.storage.StorageLevel

object NetworkWordCountStateful {
  def main(args: Array[String]) {
    //定义状态更新函数
    val updateFunc = (values: Seq[Int], state: Option[Int]) => {
      val currentCount = values.foldLeft(0)(_ + _)
      val previousCount = state.getOrElse(0)
      Some(currentCount + previousCount)
    }

    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCountStateful")

    val sc = new StreamingContext(conf, Seconds(5))
    sc.checkpoint("D:\\spark_data") //设置检查点，检查点具有容错机制
    val lines = sc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val wordDstream = words.map(x => (x, 1))
    val stateDstream = wordDstream.updateStateByKey[Int](updateFunc)
    stateDstream.print()
    sc.start()
    sc.awaitTermination()
  }

}