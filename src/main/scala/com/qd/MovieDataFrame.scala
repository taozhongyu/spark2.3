package com.qd

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Row, SparkSession, types}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

object MovieDataFrame {

  def main(args: Array[String]): Unit = {
    println("功能一：通过DataFrame实现某部电影观看者中男性和女性不同年龄的人数？")
    val dataPath = "H:\\2017studywork\\01-spark_study\\SparkScala2.2.1\\src\\main\\scala\\spark\\study01\\data\\"

    val conf = new SparkConf().setMaster("local[*]").setAppName("MovieDataFrame")
    var spark  = SparkSession.builder().config(conf).getOrCreate()
    var sc  = spark.sparkContext
    sc.setLogLevel("warn")
    //UserID::Gender::Age::Occupation::Zip-code
    val usersRDD = sc.textFile(dataPath + "users.dat")
    //MovieID::Title::Genres(风格或体裁）
    val moviesRDD = sc.textFile(dataPath + "movies.dat")
    //UserID::MovieID::Rating::Timestamp
    var ratingsRDD = sc.textFile(dataPath + "ratings.dat")
    //首先把Users的数据格式化，即在RDD的基础上增加数据的元数据信息
    val schemaForUsers = StructType("UserID::Gender::Age::Occupation::Zip-code".split("::").
      map(column => StructField(column,StringType,true)))
     //然后把每一条数据编程以Row未单位的数据
    val usersRDDRows = usersRDD.map(_.split("::")).map(line => Row(line(0).trim,line(1).trim,line(2).trim,line(3).trim,line(4).trim))
    //使用sparkSession的CreateDataFrame方法，结合Row和StructType的元数据信息
     val  userDataFrame = spark.createDataFrame(usersRDDRows,schemaForUsers);

    //也可以对StructType调用add方法来对不同的StructField赋予不同的类型
    val schemalForRatings = StructType("UserID::MovieID".split("::").map(column =>StructField(column,StringType,true)))
      .add("Rating",DoubleType,true).add("Timestamp",StringType,true)

    val ratingsRDDRows = ratingsRDD.map(_.split("::")).map(line => Row(line(0).trim,line(1).trim,line(2).trim.toDouble,line(3).trim))

    var ratingsDataFrame = spark.createDataFrame(ratingsRDDRows,schemalForRatings)

    //构建movies的DataFrame
    val schemaForMovies = StructType("MovieID::Title::Genres".split("::").map(column => StructField(column,StringType,true)))

    val moviesRDDRows = moviesRDD.map(_.split("::")).map(line=> Row(line(0).trim,line(1).trim,line(2).trim))

    val moviesDataFrame = spark.createDataFrame(moviesRDDRows, schemaForMovies)

    //通过列名称直接过滤1206的电影
    ratingsDataFrame.filter("MovieID = 1206").join(userDataFrame,"UserID")
      .select("Gender","Age") //直接通过元数据中的Gender和Age进行数据的筛选
      .groupBy("Gender","Age")  // 直接通过元数据种的Gender和Age进行数据的GroupBy操作
      .count().show(10) // 基于GroupBy分组信息进行count统计操作，并显示出分组统计后的前10条信息

    println("功能二：用LocalTempView 实现某部电影观看者中沟通不性别不同年龄分别有多少人？")
    //既然使用SQL语句，所有必须有表，所有需要把DataFrame注册为临时表
    ratingsDataFrame.createTempView("ratings")
    userDataFrame.createTempView("user")
    val sql_local = "SELECT Gender,Age,count(*) from user u join ratings as r on u.UserID =r.UserID  where  MovieID = 1193 group by Gender,Age"
    spark.sql(sql_local).show(10);


   /* ratingsDataFrame.select("MovieID","Rating").groupBy("MovieID").avg("Rating")
      .orderBy("avgRating)")
      .show(10)*/

  }
}
