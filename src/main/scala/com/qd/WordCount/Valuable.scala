package com.qd.WordCount

class Valuable(value: Int) {

  def getValue(): Int = value
}

class Pserson(value: Int) {

  def calc(): Int = value
}

//类型和方法自动适配
object Comparable {
   def add[E <: {def getValue() : Int}](l: E, r: E): Int = l.getValue() + r.getValue()
   def add2[E <: {def calc() : Int}](l: E, r: E): Int = l.calc() + r.calc()

  def main(args: Array[String]): Unit = {
    println(add(new Valuable(1), new Valuable(2)))
    println(add2(new Pserson(2), new Pserson(2)))
  }
}
