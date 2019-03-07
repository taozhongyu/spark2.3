package com.mllib
import org.apache.spark.mllib.linalg.{Matrix, Vectors}
import org.apache.spark.mllib.regression.LabeledPoint
object Statistics {

  def main(args: Array[String]): Unit = {
    val data = Seq(
      Vectors.sparse(4, Seq((0, 1.0), (3, -2.0))),
      Vectors.dense(4.0, 5.0, 0.0, 3.0),
      Vectors.dense(6.0, 7.0, 0.0, 8.0),
      Vectors.sparse(4, Seq((0, 9.0), (3, 1.0)))
    )

    //稠密矩阵
    // Create a dense vector (1.0, 0.0, 3.0).
    val dv=  Vectors.dense(1.0, 0.0, 3.0)
    //稀疏向量
    // Create a sparse vector (1.0, 0.0, 3.0) by specifying its indices and values corresponding to nonzero entries.
    val sv1= Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0))
    //稀疏向量
    // Create a sparse vector (1.0, 0.0, 3.0) by specifying its nonzero entries.
    val sv2= Vectors.sparse(3, Seq((0, 1.0), (2, 3.0)))
    println(dv)
    println(sv1)
    println(sv2)
    // Create a labeled point with a positive label and a dense feature vector.
    val pos = LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0))
    println(pos)
    // Create a labeled point with a negative label and a sparse feature vector.
    val neg = LabeledPoint(0.0, Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0)))
   println(neg)

  }

}
