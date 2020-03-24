package exercises

import lectures.part4Implicits.TypeClasses.{HTMLSerializer, User}

object EqualityPlayground extends App {
  /**
   * Equality
   */
  trait Equal[T]{
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User]{
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object FullEquality extends Equal[User]{
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

  /*
  Exercise: implement the TC pattern for the Equality tc.
   */

  object Equal{
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val john = User("John", 32, "john@rockthejvm.com")
  val anotherJohn = User("John", 45, "anotherJohn@rtjvm.com")
  println(HTMLSerializer.serialize(42))
  println(HTMLSerializer.serialize(john))

  println(HTMLSerializer[User].serialize(john))
  // AD HOC POLYMORPHISM

  /*
    Exercise - improve the Equal TC with am implicit conversion class
    ===(anotherValue: T)
    !==(anotherValue: T)
   */
  implicit class TypeSafeEqual[T](value: T){
    def ===(other: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value, other)
    def !==(other: T)(implicit equalizer: Equal[T]): Boolean = ! equalizer.apply(value, other)
  }

  println(john === anotherJohn)



}
