package lectures.part5TypeSystem

object VarianceExercises extends App{

  /**
   * Invariant, covariant, contravariant Parking[T](things List[T])
   */

  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle
  class IList[T]

  class IParking[T](vehicles: List[T]){
    def park(vehicle: T): IParking[T] = ???
    def impound(vehicles: List[T]): IParking[T] = ???
    def checkVehicles(conditions: String): List[T] = ???
  }

  class CParking[+T](vehicles: List[T]){
    def park[S >: T](vehicle: S): CParking[S] = ???
    def impount[S >: T](vehicles: List[S]): CParking[S] = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  class XParking[-T](vehicles: List[T]){
    def park(vehicle: T): XParking[T] = ???
    def impound(vehicles: List[T]): XParking[T] = ???
  }

  class CParking2[+T](vehicles: List[T]){
    def park[S >: T](vehicle: S): CParking2[S] = ???
    def impount[S >: T](vehicles: IList[S]): CParking2[S] = ???
    def checkVehicles[S >: T](conditions: String): List[S] = ???
  }

  class XParking2[-T](vehicles: List[T]){
    def park(vehicle: T): XParking[T] = ???
    def impound[S <: T](vehicles: IList[S]): XParking2[S] = ???
    def checkVehicles[S <: T](conditions: String): IList[S] = ???

    def flatMap[R <: T, S](f: Function1[R, XParking[S]]): XParking[S] = ???
  }


}



