package com.qd.log

import com.qd.uitl.GetQueryParam
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
//集成的时候必须用org.apache.spark.streaming.kafka010._包，不能使用client的包
import org.apache.spark.streaming.kafka010._

import scala.collection.mutable

object LogSparkMain {
  val regex = ",_,"

  def main(args: Array[String]): Unit = {
    /* if (args.length < 2) {
      System.err.println(
        s"""
           |Usage: DirectKafkaWordCount <brokers> <topics>
           |  <brokers> is a list of one or more Kafka brokers
           |  <topics> is a list of one or more kafka topics to consume from
           |
        """.stripMargin)
      System.exit(1)
    }
    val Array(brokers,topics)=args*/

    var conf = new SparkConf().setAppName("DirectKafka").setMaster("local[2]")
    val topics = "test"
    val ssc = new StreamingContext(conf, Seconds(10))
    ssc.checkpoint("checkpoint")
    var brokers = "192.168.2.4:9092"
    val topicsSet = topics.split(",").toSet
    val kafkaParams = mutable.HashMap[String, String]()
    //必须添加以下参数，否则会报错
    kafkaParams.put("bootstrap.servers", brokers)
    kafkaParams.put("group.id", "group1")
    kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val messages = KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topicsSet, kafkaParams)
    )

    //登录日志数据分割13列
    var logRDD = messages.map(_.value()).map(_.split(regex)).filter(_.length >= 12)
    //1.PV ,一个请求就算一个PV访问，统计，以时间维度计算(实时）2018-10-23 14:54:09--->2018-10-23 ,KEY就是日期，数据实时写到Redis
    logRDD.map(x => x(1)).filter(!_.equals("")).map(x => x.substring(0, 10)).filter(!_.equals("")).map((_, 1)).updateStateByKey[Int](updateFunc).foreachRDD(
      data => {
        data.foreach(row => {
          //计算完成存储到Redis存储24小时
          val pv = RedisClient.get("pv-day-" + row._1)
          //如果计算出的PV小于当日已存储的PV值，(断线宕机） 就要累加一下原来的
          if (null != pv && pv.toLong > row._2) {
            val data = pv.toLong + row._2
            RedisClient.setex("pv-day-" + row._1, 86400, data.toString)
          }
          else {
            RedisClient.setex("pv-day-" + row._1, 86400, row._2.toString)
          }
        })
      }
    )


    var userCount = logRDD.map(x => x(2)).filter(!_.equals("")).map(x => GetQueryParam.getQueryStr(x, "userId")).filter(!_.equals("")).map((_, 1)).updateStateByKey[Int](updateFunc).foreachRDD(
       data=>{
         data.foreach(count=>{
            RedisClient.setex("user-online-count", 86400,String.valueOf(count))
         })

       }
     )


    //1.统计用户1分钟内的登录次数，  窗口时间5S，滑动大小5S，计算周期5S
    /* var userLoginRDD = logRDD.map(x => x(2)).filter(!_.equals("")).map(x => GetQueryParam.getQueryStr(x, "userId")).filter(!_.equals("")).map((_, 1)).reduceByKeyAndWindow((x: Int, y: Int) => x + y, Seconds(60), Seconds(60)).foreachRDD(
       data => {
         data.foreach(row => {
           RedisClient.setex(row._1, 1000, row._2.toString)
         })
       }
     )*/


    //2.api实时访问量
    /*var apiLineRDD = logRDD.map(x => x(2)).filter(!_.equals("")).map(x => GetQueryParam.getApiService(x)).filter(!_.equals("")).map((_, 1)).reduceByKeyAndWindow(((x: Int, y: Int) => x + y), Seconds(10), Seconds(10)).foreachRDD(
      data => {
        data.foreach(row => {
          RedisClient.setex(row._1, 1000, row._2.toString)
        })
      }
    )*/
    //3.接口日访问量统计，以时间维度计算(实时）2018-10-23 14:54:09--->2018-10-23 ,KEY就是日期，数据实时写到Redis

    /*var apiDayCount = logRDD.map(x => x(1)).filter(!_.equals("")).map(x => x.substring(0, 10)).filter(!_.equals("")).map((_, 1)).updateStateByKey[Int](updateFunc).foreachRDD(
      data => {
        data.foreach(row => {
          RedisClient.setex("api-day-count".concat(row._1),129600,row._2.toString)
        })
      }
    )*/

    //3.系统实时访问量，以时间维度计算(实时）2018-10-23 14:54:09 ,KEY就是日期，数据实时写到Redis
    /* var hourCount = logRDD.map(x =>x(1)).filter(!_.equals("")).map(x => x.substring(11,13)).filter(!_.equals("")).map((_, 1)).reduceByKey((a,b)=>a+b).foreachRDD(
       data=>{
         data.foreach(row =>{
           val key = row._1
           //第二天要先获取数据，然后归0处理
           val count = RedisClient.get(key);
           if(null == count)
           {
             RedisClient.setex(key,7200,row._2.toString)
           }
           //如果Redis里面找到了就累加（存储36小时）
           RedisClient.setex(key,7200,(row._2+ count.toInt).toString)
         })
       }
     )*/
    ssc.start()
    ssc.awaitTermination()

  }


  val updateFunc = (values: Seq[Int], state: Option[Int]) => {
    val currentCount = values.foldLeft(0)(_ + _)
    val previousCount = state.getOrElse(0)
    Some(currentCount + previousCount)
  }
}