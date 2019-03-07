package com.qd.WordCount

//函数可以依次传入多个参数，切每传入一个参数就能生成一个可多次使用的新函数
object CurryingTest {
  def currying(left: Int)(middle: Int)(right: Int): Int = left + middle + right
  def addFunc(left:Int ,right:Int):Int = left+right;

  def main(args: Array[String]): Unit = {
    //下划线标识0个参数
    val two = currying(_)
    println(two(3)(4)(0))
    val towPlusThree = two(3)
    println(towPlusThree(4)(1))

    println(addFunc(0,1))
  }


}

