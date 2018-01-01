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
      session.sparkContext.setLogLevel("INFO")
      session
    }

    try {
      registerTables(spark)
      System.out.println(spark.sql("select * from table1").queryExecution.optimizedPlan)
    }

    finally {
      spark.stop
    }
  }
}