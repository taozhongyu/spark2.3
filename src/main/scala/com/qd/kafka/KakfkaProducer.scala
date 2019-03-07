package com.qd.kafka

import java.util.Properties

import com.alibaba.fastjson.JSONObject
import org.apache.kafka.clients.producer.{KafkaProducer, Producer, ProducerConfig, ProducerRecord}

import scala.util.Random
object KakfkaProducer {


  def main(args: Array[String]): Unit = {
    val topic = "test"
    val props = new Properties()
    props.put("bootstrap.servers", "192.168.2.4:9092")
    props.put("client.id", "Producer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)
    var i =0;
    while (i<10) {
      i = i+1
      //随机生成10以内ID
      val id = Random.nextInt(10)
      //创建订单事件
      val event = new JSONObject();
      event.put("id", id)
      event.put("price", Random.nextInt(10000))
      //发送信息
      producer.send(new ProducerRecord[String, String](topic, "key",event.toString))

      println("Message sent: " + event)
      //随机暂停一段时间
      Thread.sleep(Random.nextInt(100))
    }
  }

}