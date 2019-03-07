package com.qd.kafka

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import shapeless.PolyDefns.->

object KafkaConsumer {

  def main(args: Array[String]): Unit = {

    val props = new Properties
    props.put("client.id", "Producer")
    props.put("bootstrap.servers", "192.168.2.4:9092")
    props.put("group.id", "Producer")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList("test"))
    val records = consumer.poll(100)
    var it = records.records("test").iterator()
    while (it.hasNext)
      {
        val  consumerRecord = it.next();
        println(consumerRecord.value())
      }

  }



}