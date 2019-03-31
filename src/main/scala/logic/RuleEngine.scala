package logic

import traits.RuleTrait
import models.LogicalJoinEnum
import models.LogicalJoinEnum.{And, Or}
import models.OperationEnums
import models.OperationEnums.{Equals, NotEquals}

import scala.reflect.ClassTag

class RuleEngine[TRule <: RuleTrait, TItem <: Object](implicit classTag: ClassTag[TItem]) {
  import extensions.MethodExtensions._

  private val `class` = classTag.runtimeClass
  private val methods = this.`class`.getMethods
  private val methodTable = this.methods.map(x => x.getName -> x).toMap
  private val emptyFilterFunc = (_: TItem) => true

  def logicalJoinHandler(logicalJoin: LogicalJoinEnum.Value)(x: TItem => Boolean)(y: TItem => Boolean) = {
    logicalJoin match {
      case And => (item: TItem) => x(item) && y(item)
      case Or => (item: TItem) => x(item) || y(item)
    }
  }

  def operationHandler(operation: OperationEnums.Value)(x: Any)(y: Any) = {
    operation match {
      case Equals => x == y
      case NotEquals => x != y
    }
  }

  def build(rules: Seq[TRule]) = {
    rules.foldLeft(emptyFilterFunc)((accumulator, rule) => logicalJoinHandler(rule.logicalJoin)(accumulator)(y = (item: TItem) => operationHandler(rule.operation)(methodTable(rule.predicate).getValue(item))(rule.value)))
  }
}