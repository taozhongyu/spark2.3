package qd.WordCount

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
//./spark-submit  --class com.yh.spark.WordCountCluster --master spark:svr95:7077 --executor-memory 2g --num-executors 3  /home/yuhan/WordCountCluster.jar

object WordCount {
    def main(args: Array[String]): Unit = {
        val conf = new SparkConf()
        conf.setAppName("WordCount") //
            .setMaster("local")

        val sc = new SparkContext(conf)

        val fileName = "D:/data/word.txt"
        //获取文件内容
        val lines = sc.textFile(fileName, 1)
        //分割单词，此处仅以空格分割作为示例
        val words = lines.flatMap(line => line.split(" "))
        
        
        //String===>(word,count),count==1
        val pairs = words.map(word => (word, 1))//计数，进行MAP操作
        
        
        //(word,1)==>(word,count)
        //val result = pairs.reduceByKey((word, acc) => word + acc)
        val result = pairs.reduceByKey(_+_) //相同的KYE，进行Value的统计
        
        //result.foreach(map => println(map._1 + ":" + map._2))  //不排序直接输出结果

        //sort by count DESC
        val sorted=result.sortBy(e => { e._2 }, true , 1)  // false == desc   ture =asc 

        val mapped=sorted.map(e => (e._1, e._2))

        mapped.foreach(e => { println( e._1 + "：" + e._2 + "次") })

        sc.stop()
    }
}