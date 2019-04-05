package traits

import models.{OperationEnums, LogicalJoinEnum}

trait RuleTrait {
  val predicate: String
  val value: Array[String]
  val operation: OperationEnums.Value
  val logicalJoin : LogicalJoinEnum.Value
}
