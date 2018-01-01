package example


import org.apache.spark.SparkConf
import org.apache.spark.sql.{SparkSessionExtensions, SparkSession}
import org.apache.spark.sql.catalyst.parser.MyCatalystSqlParser

object SparkFrontend extends Tables{

  def main(args: Array[String]): Unit ={

    lazy val spark = {
      val conf = new SparkConf().setAppName("Flare").setMaster("local[1]")
      type ExtensionsBuilder = SparkSessionExtensions => Unit
      def create(builder: ExtensionsBuilder): ExtensionsBuilder = builder
      val extension = create { extensions =>
        extensions.injectParser((_, _) => MyCatalystSqlParser)
      }

      val session = SparkSession.builder().config(conf).withExtensions(extension).getOrCreate()
      session.sparkContext.setLogLevel("ERROR")
      session
    }

    try {
      registerTables(spark)
      System.out.println(spark.sql("select * from table1 knn join table2 using POINT (x2, y2) knnPred (POINT (x1, y1)" +
        ", 5)").queryExecution.optimizedPlan)

      System.out.println(spark.sql("select * from table1, table2 where x1 = x2").queryExecution.optimizedPlan)
    }

    finally {
      spark.stop
    }
  }
}