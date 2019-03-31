import org.scalatest.FlatSpec
import logic.RuleEngine
import models.{LogicalJoinEnum, OperationEnums, Person, Rule}

class RuleEngineTest extends FlatSpec {

  val ruleEngine = new RuleEngine[Rule, Person]()

  "A Stack" should "pop values in last-in-first-out order" in {
    val rules: Array[Rule] = Array(Rule("name", "Amir", OperationEnums.Equals, LogicalJoinEnum.And))
    val models = Array(Person("Amir", 123))

    val filter = ruleEngine.build(rules)

    val result = models.filter(filter)

    println(result)
  }

}
