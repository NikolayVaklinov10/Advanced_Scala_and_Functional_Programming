package lectures.part2AdvancedFunctionalProgramming

object CurriesAndPartiallyAppliedFunctions extends App{

   // curried functions
  val supperAdder: Int => Int => Int =
    x => y => x + y

  val add3 = supperAdder(3) // Int => Int = y => 3 + y
  println(add3(5))
  println(supperAdder(3)(5))


  def curriedAdder(x: Int)(y: Int): Int = x + y // curried method

  val add4: Int => Int = curriedAdder(4)
  // lifting = ETA-EXPANSION

  // FUNCTIONS != methods (JVM limitation)
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // ETA-expansion

  // Partial function application
  val add5 = curriedAdder(5) _ // Int => Int


}
