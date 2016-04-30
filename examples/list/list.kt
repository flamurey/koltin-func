package list

import com.flamurey.kfunc.collections.*

fun main(args: Array<String>) {
  val l = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  val d = l
    .tail()
    .drop(2)
    .setHead(6)
    .dropWhile { it > 5 }
    .appendLeft(List(2, 3, 4))
    .init()
    .reverse()
    .map { it - 1 }

  println(d + 11)
}