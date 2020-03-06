  package com.baizhi.wsx

  import java.text.SimpleDateFormat
  import java.util
  import java.util.Properties

  import org.apache.spark.sql.{SaveMode, SparkSession}
  import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema

  object UserReportTriggerRateApplication {
    def main(args: Array[String]): Unit = {
      /* 构建 Spark Sql 核心对象 SparkSession*/

      val spark = SparkSession.builder().appName("UserReportApplication").master("local[*]").getOrCreate()


      /*  设置日志 级别 */
      spark.sparkContext.setLogLevel("ERROR")
      /* 设置 数据源 本地*/

      val df = spark.read.json("D:\\BigData\\bigDataProjectToo_Offline\\src\\main\\resources")
      /* 查看 数据 格式*/
      df.printSchema()

      /*导入隐式转换*/

      import spark.implicits._

      /* 设置 时间格式*/
      spark.udf.register("dateCouvert",(temestamp:Long) =>{
        val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formatDate = sdf.format(temestamp)
        formatDate
      })

      df
        .select($"applicationId",$"currentTime",$"region",$"userId",$"loginSequence",$"items")
        .map( row =>{
          val applicationId = row.getAs[String]("applicationId")
          val currentTime = row.getAs[Long]("currentTime")
          val region = row.getAs[String]("region")
          val userId = row.getAs[String]("userId")
          val loginSequence = row.getAs[String]("loginSequence")

          val evalItems = row.getList(5).asInstanceOf[util.List[GenericRowWithSchema]]

          val regionEval = evalItems.get(0).getBoolean(1)
          val distanceEval = evalItems.get(1).getBoolean(1)
          val countEval = evalItems.get(2).getBoolean(1)
          val deviceEval = evalItems.get(3).getBoolean(1)
          val habitEval = evalItems.get(4).getBoolean(1)
          val passwordEval = evalItems.get(5).getBoolean(1)
          val inputFeatureEval = evalItems.get(6).getBoolean(1)

          /*  处理评估项 转换成 LiSt*/
          val list = evalItems

          (applicationId,
            currentTime,
            region,
            userId,
            loginSequence,
            regionEval,
            distanceEval,
            countEval,
            deviceEval,
            habitEval,
            passwordEval,
            inputFeatureEval)
        })
        .toDF("apId",
          "currentTime",
          "region",
          "user_id",
          "login_sequence",
          "region_eval",
          "distance_eval",
          "count_eval",
          "device_eval",
          "habit_eval",
          "passwordEval",
          "inputFeatureEval")
        .selectExpr("apId",
          "dateCouvert(currentTime) as current_time",
          "region",
          "user_id",
          "login_sequence",
          "region_eval",
          "distance_eval",
          "count_eval",
          "device_eval",
          "habit_eval",
          "passwordEval",
          "inputFeatureEval")
        .createOrReplaceTempView("t_report")

      /* 查询这个时间段内的数据*/
      val start: String = "2020-03-05 00:00:00"
      val end: String = "2020-03-05 12:08:50"
      spark
        /* 查询以上时间段内的数据 */
        .sql("select * from t_report where current_time >= '"+start+"' and current_time <= '"+end+"'")
        .createOrReplaceTempView("t_temp")


      val popr = new Properties()
      popr.put("user","root")
      popr.put("password","root")
      spark
        .sql(
          """
            | select
            |   apId,
            |   sum(case region_eval when true then 1 else 0 end )/count(*) as region_rate,
            |   sum(case distance_eval when true then 1 else 0 end)/count(*) as distance_rate,
            |   sum(case count_eval when true then 1 else 0 end)/count(*)as count_rate,
            |   sum(case device_eval when true then 1 else 0 end)/count(*) as device_rate,
            |   sum(case habit_eval when true then 1 else 0 end)/count(*)  habit_rate,
            |   sum(case passwordEval when true then 1 else 0 end)/count(*) password_rate,
            |   sum(case inputFeatureEval when true then 1 else 0 end)/count(*) inputFeature_rate
            | from
            |   t_temp
            |  group by
            |     apId
          """.stripMargin)
        .write
        .mode(SaveMode.Append)
        .jdbc("jdbc:mysql://localhost:3309/wsx","t_kwg",popr)


    }
  }
