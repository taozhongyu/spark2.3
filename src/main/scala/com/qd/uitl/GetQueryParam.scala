package com.qd.uitl

import java.util.regex.Pattern

object GetQueryParam {
  /**
    * 从URL获取用户ID
    * @param url
    * @param name
    * @return
    */
  def getQueryStr(url:String,name:String ): String =
  {
    var reg = "(^|)" + name + "=([^&]*)(\\s)"
    val p = Pattern.compile(reg)
    val matcher = p.matcher(url)
    if (matcher.find()) {
      val param = matcher.group.trim
        param.substring(param.indexOf("=")+1)
    }
    else
      {
        ""
      }
  }

  def getApiService(url:String ): String =
  {
    val  keyValPattern9 ="\\/\\w*\\/".r
    var  rest= ""
     for (elem <- keyValPattern9.findAllMatchIn("POST /usercent/orCode/getOrCodeUserInfo?appId=3&appVersion=1.0&sid=orCode2018-10-18006 HTTP/1.1")) {
         rest= elem.group(0).trim.replaceAll("/","")

     }
    rest
  }


  def main(args: Array[String]): Unit = {
    println(getApiService(" /usercent/orCode/getOrCodeUserInfo?appId=3&appVersion=1.0&sid=orCode2018-10-18006"))
  }
}
