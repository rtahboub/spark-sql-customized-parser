package example

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

trait Tables{


  def registerTables(spark: SparkSession) {

    val file_table1 = "./data/table1.csv"
    val file_table2 = "./data/table1.csv"

    val schema_table1 = StructType(Seq(
      StructField("id1", StringType, nullable = false),
      StructField("x1", DoubleType, nullable = false),
      StructField("y1", DoubleType, nullable = false)
    ))

    val schema_table2 = StructType(Seq(
      StructField("id2", StringType, nullable = false),
      StructField("x2", DoubleType, nullable = false),
      StructField("y2", DoubleType, nullable = false)
    ))

    val table1 = (spark.read
        .option("delimiter", ",")
        .option("header", "false")
        .option("inferschema", false)
        .schema(schema_table1)
        .csv(file_table1))

    val table2 = (spark.read
      .option("delimiter", ",")
      .option("header", "false")
      .option("inferschema", false)
      .schema(schema_table2)
      .csv(file_table2))

    table1.createOrReplaceTempView("table1")
    table2.createOrReplaceTempView("table2")

  }
}