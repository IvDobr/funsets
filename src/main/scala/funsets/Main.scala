package funsets

object Main extends App {
  import FunSets._

  println(contains(singletonSet(1), 1))

//    val s = (x: Int) => (x >= -10) && (x <= 10)
//    printSet(s)
//    printSet( map(s, (x: Int) => x * 10) )
}
