package lectures.part2AdvancedFunctionalProgramming

object LazyEvaluation extends App{

  lazy val x: Int = {
    println("hello")
    42
  }
  println(x)
  println(x)

  // examples of implications:
  // side effects
  def sideEffectCondition: Boolean = {
    println("Boo")
    true
  }
  def simpleCondition: Boolean = false

  lazy val LazyCondition = sideEffectCondition
  println(if (simpleCondition && LazyCondition) "yes" else "no")


  // in conjunction with call by name
  def byNameMethod(n: => Int): Int = {
    lazy val t = n  // only evaluated once
    t + t + t + 1
  }
  def retrieveMagicValue = {
    // side effect or a long computation
    println("waiting")
    Thread.sleep(1000)
    42
  }
  println(byNameMethod(retrieveMagicValue))
  // use lazy vals

  // filtering with lazy vals
  def lessThan30(i: Int): Boolean = {
    println(s"$i is less than 30?")
    i < 30
  }

  def greaterThan20(i: Int): Boolean = {
    println(s"$i is greater than 20?")
    i > 20
  }

  val numbers = List(1,25, 40, 5, 23)
  val Lt30 = numbers.filter(lessThan30) // List(1, 25, 5, 23)
  val gt20 = Lt30.filter(greaterThan20)
  println(gt20)

  val Lt30Lazy = numbers.withFilter(lessThan30) // lazy vals under the hood
  val gt20Lazy = Lt30Lazy.withFilter(greaterThan20)
  println
  gt20Lazy.foreach(println)

  // for-comprehensions use withFilter with guards
  for{
    a <- List(1,2,3) if a % 2 == 0
  } yield a + 1
  List(1,2,3).withFilter(_ % 2 == 0).map(_ + 1)

  /*
  Exercise: Implement a lazily evaluated, singly liked STREAM of elements.
   */

  abstract class MyStream[+A]{
    def isEmpty: Boolean
    def head: A
    def tail: MyStream[A]

    def #::[B >: A](element: B): MyStream[B] // prepend operator
    def ++[B >: A](anotherStream: MyStream[B]): MyStream[B] // concatenate two streams

    def foreach(f: A => Unit): Unit
    def map[B](f: A => B): MyStream[B]
    def flatMap[B](f: A => MyStream[B]): MyStream[B]
    def filter(predicate: A => Boolean): MyStream[A]

    def take(n: Int): MyStream[A] // takes the first n elements out of this stream
    def takeAsList(n: Int): List[A]
  }

  object MyStream{
    def from[A](start: A)(generator: A => A): MyStream[A] = ???
  }







}





