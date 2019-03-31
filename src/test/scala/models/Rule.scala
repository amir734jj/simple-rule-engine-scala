package models

import traits._

case class Rule(predicate: String, value: String, operation: OperationEnums.Value, logicalJoin: LogicalJoinEnum.Value) extends RuleTrait