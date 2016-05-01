package com.flamurey.kfunc.core

interface Monoid<A> {
  fun op(l: A, r: A): A
  fun zero(): A

  companion object Factory {
    fun getStringMonoid() = object : Monoid<String> {
      override fun op(l: String, r: String) = l + r
      override fun zero(): String = ""
    }

    fun getIntAddition() = object : Monoid<Int> {
      override fun op(l: Int, r: Int): Int = l + r
      override fun zero(): Int = 0
    }

    fun getIntMultiplication() = object : Monoid<Int> {
      override fun op(l: Int, r: Int): Int = l * r
      override fun zero(): Int = 1
    }

    fun getBooleanOr() = object : Monoid<Boolean> {
      override fun op(l: Boolean, r: Boolean) = l || r
      override fun zero() = false
    }

    fun getBooleanAnd() = object : Monoid<Boolean> {
      override fun op(l: Boolean, r: Boolean) = l && r
      override fun zero() = true
    }

    fun <T> getOptionMonoid(m: Monoid<T>) = object : Monoid<Option<T>> {
      override fun zero() = Option(m.zero())

      override fun op(l: Option<T>, r: Option<T>): Option<T> {
        val lValue = l.getOrElse(m.zero())
        val rValue = r.getOrElse(m.zero())
        return Option(m.op(lValue, rValue))
      }
    }

    fun <A, B> getProductMonoid(a: Monoid<A>, b: Monoid<B>) = object : Monoid<Pair<A, B>> {
      override fun zero(): Pair<A, B> = a.zero() to b.zero()

      override fun op(l: Pair<A, B>, r: Pair<A, B>) = a.op(l.first, r.first) to b.op(l.second, r.second)
    }
  }
}



