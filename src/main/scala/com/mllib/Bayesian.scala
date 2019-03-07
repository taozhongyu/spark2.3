package com.mllib


import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}
object Bayesian {
  def main(args: Array[String]) {
  // http://www.kaggle.com/c/stumbleupon/data 中的（train.txt和test.txt
    val conf = new SparkConf().setMaster("local[2]").setAppName("Bayesian")
    val sc = new SparkContext(conf)
    val orig_file=sc.textFile("D:/data/all/train.tsv")

    val ndata_file=orig_file.map(_.split("\t")).map{
      r =>
        val trimmed =r.map(_.replace("\"",""))
        val lable=trimmed(r.length-1).toDouble
        val feature = trimmed.slice(4,r.length-1).map(d => if(d=="?")0.0
        else d.toDouble).map(d =>if(d<0) 0.0 else d)
        LabeledPoint(lable,Vectors.dense(feature))
    }.randomSplit(Array(0.7,0.3),11L)//划分为训练和测试数据集
    val ndata_train=ndata_file(0).cache()//训练集
    val ndata_test=ndata_file(1)//测试集

    val model_NB=NaiveBayes.train(ndata_train)
    /*贝叶斯分类结果的正确率*/
    val correct_NB=ndata_train.map{
      point => if(model_NB.predict(point.features)==point.label)
        1
      else 0
    }.sum()/ndata_train.count()//0.565959409594096
    /*准确率 - 召回率（ PR ）曲线*和ROC 曲线输出*/
    val metricsNb=Seq(model_NB).map{
      model =>
        val socreAndLabels=ndata_train.map {
          point => (model.predict(point.features), point.label)
        }
        val metrics=new BinaryClassificationMetrics(socreAndLabels)
        (model.getClass.getSimpleName,metrics.areaUnderPR(),metrics.areaUnderROC())
    }
    metricsNb.foreach{ case (m, pr, roc) =>
      println(f"$m, Area under PR: ${pr * 100.0}%2.4f%%, Area under ROC: ${roc * 100.0}%2.4f%%")
    }

  }

}
