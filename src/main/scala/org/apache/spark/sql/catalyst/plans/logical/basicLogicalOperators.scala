package org.apache.spark.sql.catalyst.plans.logical


import org.apache.spark.sql.catalyst.expressions._
import org.apache.spark.sql.catalyst.plans.logical.statsEstimation.JoinEstimation
import org.apache.spark.sql.internal.SQLConf
import org.apache.spark.util.Utils
import org.apache.spark.sql.catalyst.plans._
import org.apache.spark.sql.types.{BooleanType, DataType}


//adapted from Simba: https://github.com/InitialDLab/Simba

case class SpatialJoin(left: LogicalPlan, right: LogicalPlan, joinType: SpatialJoinType,
                       condition: Option[Expression]) extends BinaryNode {
  override def output: Seq[Attribute] = {
    joinType match {
      case KNNJoin =>
        left.output ++ right.output
      case _ =>
        left.output ++ right.output
    }
  }

  def selfJoinResolved: Boolean = left.outputSet.intersect(right.outputSet).isEmpty

  // Joins are only resolved if they don't introduce ambiguous expression ids.
  override lazy val resolved: Boolean = {
    childrenResolved &&
      expressions.forall(_.resolved) &&
      selfJoinResolved &&
      condition.forall(_.dataType == BooleanType)
  }
}