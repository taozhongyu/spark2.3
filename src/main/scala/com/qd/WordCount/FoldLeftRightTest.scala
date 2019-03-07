package com.qd.WordCount

object FoldLeftRightTest {
  def main(args: Array[String]) {
    val list = List(1, 3, 5)
    //折叠操作是一个递归的过程，将上一次的计算结果代入到函数中
    //作为结果的参数在foldLeft是第一个参数，在foldRight是第二个参数

    //foldLeft表示从左向右折叠，从最左边的元素向最右边的元素折叠
    val c = list.foldLeft("String:")((x: String, y: Int) => x + y) //String:135
    println(c)
    val d = list.foldLeft(100)((x: Int, y: Int) => x + y) //109
    println(d)
    val e = list.foldLeft(0)((x: Int, y: Int) => x + y)
    println(e)
    val f = list.foldLeft(2)(( y: Int,x:Int) =>  x * y )
    println(f)
   var aInt =1


    //https://blog.csdn.net/onwingsofsong/article/details/77822920
  }
}