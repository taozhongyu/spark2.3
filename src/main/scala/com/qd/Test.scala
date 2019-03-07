package com.qd

object Test {

  def main(args: Array[String]): Unit = {

    var increase=(x:Int)=>{x+1}
    val someNumbers = List ( -11, -10, - 5, 0, 5, 10)
    someNumbers.foreach( x=>println(x))

   var b = someNumbers.foreach(increase)
    println(b.getClass)
    var c = someNumbers.map(increase)
    println(c.getClass)

    c.foreach((x:Int)=>print(x+" "))

    //返回空的括号()
    val d= c.map((x:Int)=>println(x))


    d.foreach(x=>println(x))
    println(d.getClass)
  }
}
