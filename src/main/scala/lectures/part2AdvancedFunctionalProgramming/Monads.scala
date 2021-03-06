package lectures.part2AdvancedFunctionalProgramming

object Monads extends App{

  // own Try monad
  trait Attempt[+A] {
    def flatMap[B](f: A => Attempt[B]): Attempt[B]
  }
  object Attempt {
    def apply[A](a: => A): Attempt[A] =
      try {
        Success(a)
      }catch {
        case e: Throwable => Fail(e)
      }
  }

  //Try contains the following case classes
  case class Success[+A](value: A) extends Attempt[A]{
    def flatMap[B](f: A => Attempt[B]): Attempt[B] =
      try{
        f(value)
      }catch {
        case e: Throwable => Fail(e)
      }
  }

  case class Fail(e: Throwable) extends Attempt[Nothing] {
    def flatMap[B](f: Nothing => Attempt[B]): Attempt[B] = ???
  }

  /*
  unit.flatMap(f) = f(x)
  Attempt(x).flatMap(f) = f(x) // Success case!
  Success(x).flatMap(f) = f(x)  // proved.

  right-identity
  attempt.flatMap(unit)  = attempt
  Success(x).flatMap(x => Attempt(x)) = Attempt(x) = Success(x)
  Fail(_).flatMap(...) = Fail(e)

  attempt.flatMap(f).flatMap(g) == attempt.flatMap(x => f(x).flatMap(g))


   */

  val attempt = Attempt{
    throw new RuntimeException("My own monad, yes!")
  }

  /*

   */

  // 1 - lazy monad
  class Lazy[+A](value: => A){
    // call by need
    private lazy val internalValue = value
    def use: A = internalValue
    def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(internalValue)
  }
  object Lazy {
    def apply[A](value: => A): Lazy[A] = new Lazy(value)  // unit
  }

  val LazyInstance = Lazy {
    println("Today I don't feel like doing anything")
    42
  }


  val flatMappedInstance = LazyInstance.flatMap(x => Lazy {
    10 * x
  })

  val flatMappedInstance2 = LazyInstance.flatMap(x => Lazy {
    10 * x
  })

  flatMappedInstance.use
  flatMappedInstance2.use

}
