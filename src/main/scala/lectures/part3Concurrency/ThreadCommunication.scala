package lectures.part3Concurrency

import scala.collection.mutable
import scala.util.Random

object ThreadCommunication extends App{

  /*
  the producer-consumer problem
  producer thread     consumer thread
  producer -> [ ? ] -> consumer
   */
  class SimpleContainer{
    private var value: Int = 0

    def isEmpty: Boolean = value == 0

    // producing method
    def set(newValue: Int) = value = newValue

    // consuming method
    def get = {
      val result = value
      value = 0
      result
    }
  }

  def naiveProdCons(): Unit = {
    // the container
    val container = new SimpleContainer

    // instantiating 2 threads
    val consumer = new Thread(() => {
      println("[consumer ] waiting...")
      while (container.isEmpty){
        println("[consumer] actively waiting ...")
      }
      println("[consumer] I have consumed "+ container.get)
    })

    val producer = new Thread(() => {
      println("[producer] computing...")
      Thread.sleep(500)
      val value = 42
      println("[producer] I have produced, after long work, the value " + value)
      container.set(value)
    })
    consumer.start()
    producer.start()
  }
//  naiveProdCons()

  // refactor the come with the following new tools:
  // wait and notify
  def smartProdCons(): Unit = {
    val container = new SimpleContainer

    val consumer = new Thread(() => {
      println("[consumer] waiting...")
      container.synchronized{
        container.wait()
      }

      // at this point the container must have some value
      println("[consumer] I have consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("[producer] Hard at work...")
      Thread.sleep(2000)
      val value = 42

      container.synchronized{
        println("[producer] I'm producing " + value)
        container.set(value)
        container.notify()
      }
    })
//    consumer.start()
//    producer.start()
  }

//  smartProdCons()


  /*
    producer -> [ ? ? ? ] -> consumer
   */

  def producerConsLargeBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()

      while (true){
        buffer.synchronized{
          println("[consumer] buffer empty, waiting ...")
          buffer.wait()
        }

        // there must be at least ONE value in the buffer
        val x = buffer.dequeue()
        println("[consumer] consumed " + x)

        // hey producer, there's empty space available, are you lazy?!
        buffer.notify()

      }

      Thread.sleep(random.nextInt(250))
    })

    val producer = new Thread(() => {
      val random = new Random()
      var i = 0

      while(true){
        buffer.synchronized{
            if(buffer.size == capacity){
              println("[producer] buffer is full, waiting...")
              buffer.wait()
            }

          // at this point there must be at least ONE EMPTY SPACE in the buffer
          println("[producer] producing " + i)
          buffer.enqueue(i)

          // hey consumer, new food for you!
          buffer.notify()

          i += 1
        }
        Thread.sleep(random.nextInt(500))
      }
    })
    consumer.start()
    producer.start()
  }
  producerConsLargeBuffer()



}
