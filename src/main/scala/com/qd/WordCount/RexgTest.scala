package com.qd.WordCount

import scala.util.matching.Regex

object RexgTest {
  def main(args: Array[String]): Unit = {
    /*
    /*val numberPattern: Regex = "[0-9]".r
    numberPattern.findFirstMatchIn("12323") match
    {
      case Some(_) => println("Password OK")
      case None => println("Password must contain a number")
    }
  }*/

    val keyValPattern = "([0-9a-zA-Z-#() ]+): ([0-9a-zA-Z-#() ]+)".r
    val input: String =
      """background-color: #A03300;
        |background-image: url(img/header100.png);
        |background-position: top center;
        |background-repeat: repeat-x;
        |background-size: 2160px 108px;
        |margin: 0;
        |height: 108px;
        |width: 100%;""".stripMargin
    for (patternMatch <- keyValPattern.findAllMatchIn(input))
      println(s"key: ${patternMatch.group(1)} value: ${patternMatch.group(2)}")

    //元字符必须要多一个转义符合: 匹配以字母a开始，中间任务字符，以任务字符结束
    val keyValPattern1 = "\\ba\\w*\\b".r
    for (elem <- keyValPattern1.findAllMatchIn("a1232ba1111")) {
      println(elem.group(0))
    }

    println(keyValPattern1.findAllMatchIn("a2324abc").length>0)

    val keyValPattern2 = "\\b\\w{6}\\b".r
    println(keyValPattern2.findAllMatchIn("abc123&&").length>0)
    println(keyValPattern2.findAllMatchIn("123456").length>0)


    val keyValPattern3 = "^\\d{5,12}$".r //5-12位连续的数字
    println(keyValPattern3.findAllMatchIn("123456").length>0)
    println(keyValPattern3.findAllMatchIn("abcd2323").length>0)//不满足

    val keyValPattern4 = "Windows\\d{4}+".r
    println(keyValPattern4.findAllMatchIn("Windows1114").length>0)
    println(keyValPattern4.findAllMatchIn("windows1114").length>0)


    val keyValPattern5 = "[.?!]".r
    println(keyValPattern5.findAllMatchIn("...11").length>0)
    println(keyValPattern5.findAllMatchIn("111").length>0)

    val keyValPattern6 = "0\\d{2}-\\d{8}|0\\d{3}-\\d{7}".r
    println(keyValPattern6.findAllMatchIn("0551-1234567").length>0)
    println(keyValPattern6.findAllMatchIn("0551-123456A").length>0)
   //分枝条件
    val  keyValPattern7 ="(0\\d{2})[-]\\d{8}|0\\d{3}[-]\\d{8}".r
    println(keyValPattern7.findAllMatchIn("0255-12345678").length>0)
    println(keyValPattern7.findAllMatchIn("029-12345678").length>0)
   //分组
   val  keyValPattern8 ="\\b\\w*\\/\\w*\\[^?]".r
   val  keyValPattern9 ="\\/\\w*\\/".r
   // println(keyValPattern8.findAllMatchIn("/usercent/orCode/getOrCodeUserInfo?").length>0)
    for (elem <- keyValPattern9.findAllMatchIn("POST /usercent/orCode/getOrCodeUserInfo?appId=3&appVersion=1.0&sid=orCode2018-10-18006 HTTP/1.1")) {
      println(elem.group(0).trim)
    }
  }
*/


/*
  val keyVal10 = "^1\\d{5}".r
  println(keyVal10.findAllMatchIn("123456").length>0)

    val keyVal12 = "[1-9]{3}\\1".r;
    println(keyVal12.findAllMatchIn("111222").length>0)

*/

    val keyVal12 = "^#?([a-f0-9]{6}|[a-f0-9]{3})$".r
    // 匹配16进制的格式：?匹配这个之前的表达式0次或一次，在这里的意思就是可以有#，也可以没有都可以支持, | 长度6或长度3两种类型
       /*println(keyVal12.findAllMatchIn("abcde0").length>0)
       println(keyVal12.findAllMatchIn("abc").length>0)
       println(keyVal12.findAllMatchIn("250").length>0)
       println(keyVal12.findAllMatchIn("25444443").length>0)*/

    println("emailRex0:")
     //0-9a-zA-Z]字符串的开始是数字或字母,[ ;
    // [-.\w]* 表示下划线和点任意字符可以出现0次或多次
    // [0-9a-zA-Z] 数字或字母
    // + 表示签名的表达式可以出现1次或多次，但至少一次
    //@ 返回后面的内容
    //(([-\\w]*  匹配字母或数字或下划线或汉字 [0-9a-zA-Z] 数字或字母任意组合 )* 重复0次或多次 .)+ 必须重复一次或多次，后面的是字母必须2-9位

    val emailRex0= "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@(([0-9a-zA-Z])+([-\\w]*[0-9a-zA-Z])*\\.)+[a-zA-Z]{2,9})$".r
      println(emailRex0.findAllMatchIn("taozhongyu@126.com").length>0)
    println(emailRex0.findAllMatchIn("t.--..2.22@126.com").length>0)
    for (elem <- emailRex0.findAllMatchIn("taozhongyu@126.com.cn")) {
      println(elem.group(0).trim)
    }


    //循环根据集合中的字符从要匹配的串里面找，如下会找到三个字符串
    val emailRex2 ="[abc]".r
    for (elem <- emailRex2.findAllMatchIn("plabcin")) {
      println(elem.group(0).trim)
    }
    val emailRex3 ="^[abc]+".r
    for (elem <- emailRex3.findAllMatchIn("abc")) {
      println(elem.group(0).trim)
    }

    //https://www.jb51.net/tools/zhengze.html#getstarted
    val pswdRex =".(2[0-4]\\d|25[0-4]|[01]?\\d\\d?)".r
    for (elem <- pswdRex.findAllMatchIn(".255")) {
      println(elem.group(0).trim)
    }

    var wordRex = "\\b(\\w+)\\b\\s+\\1\\b".r
    for (elem <- wordRex.findAllMatchIn("go go")) {
      println(elem.group(0).trim)
    }
    //捕获  (?<name>exp)	匹配exp,并捕获文本到名称为name的组里，也可以写成(?'name'exp)
    var wordRex2 = "(?<go>\\w)".r
    for (elem <- wordRex2.findAllMatchIn("go go")) {
      println(elem.group(0).trim)
    }



    //"中国人".replace(/(?<=中国)人/, "rr") // 匹配中国人中的人，将其替换为rr，结果为 中国rr
   // "法国人".replace(/(?<=中国)人/, "rr") // 结果为 法国人，因为人前面不是中国，所以无法匹配到

    /***
      * 前瞻：
      * exp1(?=exp2) 查找exp2前面的exp1
      * 后顾：
      * (?<=exp2)exp1 查找exp2后面的exp1
      * 负前瞻：
      * exp1(?!exp2) 查找后面不是exp2的exp1
      * 负后顾：
      * (?<!=exp2)exp1 查找前面不是exp2的exp1
      *
      */
   /* //开始和结尾匹配
    val emailRex4 ="^[abc]+$".r
    for (elem <- emailRex4.findAllMatchIn("abc")) {
      println(elem.group(0).trim)
    }

    //开始和结尾匹配
    println("emailRex5:")
    val emailRex5 ="^(abc)\\w{0,}".r
    for (elem <- emailRex5.findAllMatchIn("abcdefabc")) {
      println(elem.group(0).trim)
    }
    println("httpReg:")
    val httpReg = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w\\.-]*)*\\?$";
    for (elem <- emailRex5.findAllMatchIn("https://www.cnblogs.com/sunny3096/p/7201403.html")) {
      println(elem.group(0).trim)
    }
    println("testReg:")
    //不包含集合中的内容
    val testReg = "[^?#]".r
    for (elem <- testReg.findAllMatchIn("##?2@#")) {
      println(elem.group(0).trim)
    }
    println("tesvtReg2:")
    val tesvtReg2 = "llo(?= World)".r
    for (elem <- testReg.findAllMatchIn("Hello World!Hello")) {
      println(elem.group(0).trim)
    }
*/
  }
}
