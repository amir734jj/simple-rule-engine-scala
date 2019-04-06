# simple-rule-engine-scala

Simple rule engine written in Scala that uses reflection so it is slow ... 

Example:

```scala
// Simple class definition
case class Person(name: String, age: Int)

// Create engine instance
val ruleEngine = new RuleEngine[Rule, Person]()

// Rules need to implement `RuleTrait`
// Don't worry about using Array of string as values, code will
// handle type-conversion
val rules: Array[Rule] = Array(
  Rule("name", Array("Test"), Equals, And),
  Rule("age", Array("123"), Equals, And)
)

// Make some objects
val models = Array(
  Person("Test", 123),
  Person("Not test!", 123)
)

// Create the `Person => Boolean` lambda
val filter = ruleEngine.build(rules)

// Run the lambda against data
val result = models.filter(filter)

// Assert count
assert(result.length == 1)
```
