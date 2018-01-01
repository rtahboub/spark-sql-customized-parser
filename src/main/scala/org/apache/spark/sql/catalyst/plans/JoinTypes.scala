package org.apache.spark.sql.catalyst.plans

import java.util.Locale
import org.apache.spark.sql.catalyst.expressions.Attribute

//adapted from Simba: https://github.com/InitialDLab/Simba

object SpatialJoinType {
  def apply(typ: String): SpatialJoinType = typ.toLowerCase.replace("_", "") match {
    case "knn" => KNNJoin
    case _ =>
      val supported = Seq("knn")

      throw new IllegalArgumentException(s"Unsupported spatial join type '$typ'. " +
        "Supported spatial join types include: " + supported.mkString("'", "', '", "'") + ".")
  }
}

sealed abstract class SpatialJoinType

case object KNNJoin extends SpatialJoinType
