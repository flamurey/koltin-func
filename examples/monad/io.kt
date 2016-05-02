package monad

import com.flamurey.kfunc.core.PrintLine
import com.flamurey.kfunc.core.ReadLine

fun main(args: Array<String>) {
  val echo = ReadLine().flatMap { PrintLine(it) }
  echo.get()
  echo.get()
}