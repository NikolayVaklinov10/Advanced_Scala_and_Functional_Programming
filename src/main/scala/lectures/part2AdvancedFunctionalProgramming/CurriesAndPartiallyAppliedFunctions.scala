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


  // Exercise
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  // add7: Int => Int = y => 7 + y
  // as many different implementations of add7 using the above
  val add7 = (x: Int) => simpleAddFunction(7, x) // simplest
  val add7_2 = simpleAddFunction.curried(7)
  val add7_6 = simpleAddFunction(7, _: Int) // works as well

  val add7_3 = curriedAddMethod(7) _ // PAF
  val add7_4 = curriedAddMethod(7)(_) // PAF = alternative syntax

  val add_5 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into functions values
                // y => simpleAddMethod(7, y)

  // uderscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _: String, ", how are you?") // x: String => concatenator(hello, x, how are you)
  println(insertName("Daniel"))

  val fillInTheBlanks = concatenator("Hello, ", _: String, _: String) // (x,y) => concatenator(hello, x, y)

  // Exercises
  /*

  1. Process a list of numbers and return their string representations with different formats
      Use the %4.2f, %8.6f and %14.12f with a curried formatter function.
   */
  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _ // lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println(numbers.map(simpleFormat))

  /*
  2. difference between
      - functions vs methods
      - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parentMethod(): Int = 42

  /*
  calling byName and byFunction
    - int
    - method
    - parenMethod
    - lambda
    - PAF
   */
  byName(23) // OK
  byName(method) // OK
  byName(parentMethod())
  byName(parentMethod) // OK BUT beware ==> byName(parenMethod())
  // byName(() => 42) // NOT OK
  byName((() => 42)()) // OK
  // byName(parenMethod _) // not OK

  // byFunction(45) // not ok
  // byFunction(method) // not ok !!!!!! does not do ETA-expresion!
  byFunction(parentMethod) // works
  byFunction(parentMethod _) // also works, but warning - unnecessary
}







