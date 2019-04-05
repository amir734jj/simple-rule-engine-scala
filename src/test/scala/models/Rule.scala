package models

import traits._

case class Rule(predicate: String, value: Array[String], operation: OperationEnums.Value, logicalJoin: LogicalJoinEnum.Value) extends RuleTrait