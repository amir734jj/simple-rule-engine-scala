package logic

import traits.RuleTrait
import models.LogicalJoinEnum
import models.LogicalJoinEnum.{And, Or}
import models.OperationEnums
import models.OperationEnums.{Equals, NotEquals, In, NotIn}
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

  def operationHandler(operation: OperationEnums.Value)(fieldValue: Any)(ruleValue: Array[String]) = {
    operation match {
      case Equals => ruleValue.headOption match {
        case Some(head) => typeConversion(fieldValue)(head) == fieldValue
        case None => false
      }
      case NotEquals => ruleValue.headOption match {
        case Some(head) => typeConversion(fieldValue)(head) != fieldValue
        case None => false
      }
      case In => ruleValue.exists(x => typeConversion(fieldValue)(x) == fieldValue)
      case NotIn => !ruleValue.exists(x => typeConversion(fieldValue)(x) == fieldValue)
    }
  }

  def typeConversion(fieldValue: Any)(value: String): Any = {
    fieldValue match {
      case _: Short => value.toShort
      case _: Int => value.toInt
      case _: Float => value.toFloat
      case _: Double => value.toDouble
      case _: Boolean => value.toBoolean
      case _: String => value
    }
  }

  def build(rules: Seq[TRule]) = {
    rules.foldLeft(emptyFilterFunc)((accumulator, rule) => logicalJoinHandler(rule.logicalJoin)(accumulator)(y = (item: TItem) => operationHandler(rule.operation)(methodTable(rule.predicate).getValue(item))(rule.value)))
  }
}
