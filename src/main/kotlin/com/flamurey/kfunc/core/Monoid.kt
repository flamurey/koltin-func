package com.flamurey.kfunc.core

import java.util.*

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

    fun <T> getOptionMonoid(m: Monoid<T>) = object : Monoid<Optional<T>> {
      override fun zero() = Optional.of(m.zero())

      override fun op(l: Optional<T>, r: Optional<T>): Optional<T> {
        val lValue = if (l.isPresent) l.get() else m.zero()
        val rValue = if (r.isPresent) r.get() else m.zero()
        return Optional.of(m.op(lValue, rValue))
      }
    }
  }
}



