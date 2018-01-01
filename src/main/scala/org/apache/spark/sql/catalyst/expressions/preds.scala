package org.apache.spark.sql.catalyst.expressions

import org.apache.spark.sql.catalyst.InternalRow
import org.apache.spark.sql.catalyst.analysis.TypeCheckResult
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenContext
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenContext
import org.apache.spark.sql.catalyst.expressions.codegen.CodegenFallback
import org.apache.spark.sql.catalyst.expressions.codegen.ExprCode
import org.apache.spark.sql.catalyst.expressions.codegen.ExprCode
import org.apache.spark.sql.catalyst.util.TypeUtils
import org.apache.spark.sql.types.AbstractDataType
import org.apache.spark.sql.types.AnyDataType
import org.apache.spark.sql.types.MapType
import org.apache.spark.sql.types.{BooleanType, DataType}

import scala.collection.immutable.::


case class PredKnn(point1: Seq[Expression], point2: Seq[Expression], k: Expression) extends Predicate {
  override def children: Seq[Expression] = k +: (point1 ++ point2)

  override def nullable: Boolean = children.exists(_.nullable)

  override def foldable: Boolean = children.forall(_.foldable)

  override def toString: String = s"PredKnn (POINT${
    point1.mkString("(", ",",
      ")")
  }, POINT${point2.mkString("(", ",", ")")}, $k)"


  //  empty implementation
  override def eval(input: InternalRow): Any = false

  //  empty implementation
  override def doGenCode(ctx: CodegenContext, ev: ExprCode): ExprCode = {
    val valueGen = k.genCode(ctx)
    ev.copy(code =
      s"""
      ${valueGen.code}
      ${ev.value} = false;
      ${ev.isNull} = ${valueGen.isNull}
    """)
  }
}