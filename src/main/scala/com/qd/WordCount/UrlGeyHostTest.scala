package com.qd.WordCount

import java.util.regex.Pattern

/**
  * Created by Administrator on 2017/9/26.
  */
object UrlGeyHostTest {
  def main(args: Array[String]): Unit = {
    //传参
    val url1 = "http://www.tieba.baidu.com/p/4336698825"
    val url2 = "http://mp.weixin.qq.com/s?__biz=MzIyODgyNDk0OQ==&mid=2247483988&idx=3&sn=7181bbef257e27014051272d785eeafd&scene=4#wechat_redirect"
    var host = ""
    //val reg = "(^|&)" + "mid" + "=([^&]*)(&|$)"
    var name ="mid"
    var reg = "(^|)" + name + "=([^&]*)"
    val p = Pattern.compile(reg)
    val matcher = p.matcher(url2)
    if (matcher.find()) {
      host = matcher.group()
    }
    println(host)
  }

}