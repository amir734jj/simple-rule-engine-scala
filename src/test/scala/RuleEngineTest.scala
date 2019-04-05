import org.scalatest.FlatSpec
import logic.RuleEngine
import models.{Person, Rule}
import models.LogicalJoinEnum.{And, Or}
import models.OperationEnums.{Equals}

class RuleEngineTest extends FlatSpec {

  val ruleEngine = new RuleEngine[Rule, Person]()

  "RuleEngine" should "find no match" in {
    val rules: Array[Rule] = Array(
      Rule("name", Array("XXX"), Equals, Or),
      Rule("age", Array("999"), Equals, And)
    )

    val models = Array(Person("Test", 123), Person("Not test!", 123))

    val filter = ruleEngine.build(rules)

    val result = models.filter(filter)

    assert(result.length == 0)
  }

  "RuleEngine" should "find single match" in {
    val rules: Array[Rule] = Array(
      Rule("name", Array("Test"), Equals, And),
      Rule("age", Array("123"), Equals, And)
    )

    val models = Array(Person("Test", 123), Person("Not test!", 123))

    val filter = ruleEngine.build(rules)

    val result = models.filter(filter)

    assert(result.length == 1)
  }

  "RuleEngine" should "find multiple match" in {
    val rules: Array[Rule] = Array(
      Rule("name", Array("Test"), Equals, Or),
      Rule("age", Array("123"), Equals, And)
    )

    val models = Array(Person("Test", 123), Person("Not test!", 123))

    val filter = ruleEngine.build(rules)

    val result = models.filter(filter)

    assert(result.length == 2)
  }
}
