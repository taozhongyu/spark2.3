package com.qd.log

import java.util

import redis.clients.jedis.{HostAndPort, JedisCluster, JedisPoolConfig}

import scala.collection.JavaConversions._
import scala.collection.mutable
object RedisClient extends Serializable {


  def main(args: Array[String]): Unit = {

    RedisClient.setex("name1",100,"taozy");
    println(RedisClient.get("name").toString)
  }

  val poolConfig = new JedisPoolConfig
  // 最大连接数
  poolConfig.setMaxTotal(1)
  // 最大空闲数
  poolConfig.setMaxIdle(1)
  // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
  // Could not get a resource from the pool
  poolConfig.setMaxWaitMillis(1000)
  val nodes = new util.LinkedHashSet[HostAndPort]
  nodes.add(new HostAndPort("192.168.2.4", 7000))
  nodes.add(new HostAndPort("192.168.2.5", 7000))
  nodes.add(new HostAndPort("192.168.2.6", 7000))
  nodes.add(new HostAndPort("192.168.2.4", 7001))
  nodes.add(new HostAndPort("192.168.2.5", 7001))
  nodes.add(new HostAndPort("192.168.2.6", 7001))
  val clients = new JedisCluster(nodes, 10000, 1000, 1000, "passwd123", poolConfig)


  /**
    *
    * @param key
    * @param value
    * @return
    */

  def set(key: String, value: Any): Unit = {

    clients.set(key, String.valueOf(value))


  }
  def get(key: String): String = {
    val rest = clients.get(key)
    rest
  }


  def setex(key: String, seconds: Int, value: String): Unit = {

    clients.setex(key, seconds,value)

  }

  /**
    *
    * @param key
    * @param time
    * @return
    */

  def hget(key: String, time: Long): Option[String] = {

    val value = clients.hget(key, String.valueOf(time))
    if (value == null)
      None
    else
      Some(value)

  }

  /**
    *
    * @param key
    * @param time
    * @param value
    * @return
    */
  def hset(key: String, time: Long, value: Any): Boolean = {
    clients.hset(key, String.valueOf(time), String.valueOf(value)) == 1

  }

  def hmset(key: String, map: mutable.Map[String, String]): Unit = {
    val map2 = mutable.Map[String, String]()
    map.foreach { case (key: String, value: String) =>
      map2.put(key, value)
    }

    clients.hmset(key, mapAsJavaMap(map2))

  }

  /**
    *
    * @param key
    * @param time
    * @return
    */
  def hdel(key: String, time: Any): Option[Long] = {

    Some(clients.hdel(key, String.valueOf(time)))

  }

  /**
    *
    * @param key
    * @param times
    * @return
    */
  def rpush(key: String, times: Any): Option[Long] = {

    Some(clients.rpush(key, String.valueOf(times)))

  }


  /**
    *
    * @param key
    * @return
    */
  def lpop(key: String): Option[Long] = {

    val time = clients.lpop(key)
    if (time == null)
      None
    else
      Some(time.toLong)
  }


  /**
    *
    * @param key
    * @return
    */
  def lhead(key: String): Option[Long] = {

    val head = clients.lindex(key, 0)
    if (head == null)
      None
    else
      Some(head.toLong)
  }

}