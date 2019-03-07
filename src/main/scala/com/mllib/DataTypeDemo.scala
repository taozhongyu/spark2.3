package com.mllib

object DataTypeDemo {
  def main(args: Array[String]): Unit = {
    for( a <- 1 to 10){
      for(b<-1 to 10 )
        {
          print(a*b)
          print(",")
        }
      println("")
    }
  }

}
