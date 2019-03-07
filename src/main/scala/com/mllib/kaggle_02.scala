package com.mllib

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession


object kaggle_02 {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[2]").setAppName("kaggel-02")
    var spark = SparkSession.builder().config(conf).getOrCreate()
    var sc = spark.sparkContext

    val trainRDD = spark.read.option("header", "true").csv("D:\\data\\kaggle_02\\train.csv")
    val testRDD = spark.read.option("header", "true").csv("D:\\data\\kaggle_02\\test.csv")


/*    val ndata_file=trainRDD.map(_.split("\t")).map{
      r =>
        val trimmed =r.map(_.replace("\"",""))
        val lable=trimmed(r.length-1).toDouble
        val feature=trimmed.slice(4,r.length-1).map(d => if(d=="?")0.0
        else d.toDouble).map(d =>if(d<0) 0.0 else d)
        LabeledPoint(lable,Vectors.dense(feature))
    }.randomSplit(Array(0.7,0.3),11L)//划分为训练和测试数据集
    val ndata_train=ndata_file(0).cache()//训练集
    */
    trainRDD.show();

  }


}
