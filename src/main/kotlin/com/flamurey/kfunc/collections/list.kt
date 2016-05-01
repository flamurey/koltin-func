package com.flamurey.kfunc.collections

import com.flamurey.kfunc.core.Monoid

sealed class List<out T> {
  var hashCode: Int = 0

  object Nil : List<Nothing>()

  class Cons<out T>(val head: T, val tail: List<T>) : List<T>()

  final fun drop(n: Int): List<T> {
    tailrec fun loop(nn: Int, l: List<T>): List<T> =
      if (nn == 0) l
      else when (l) {
        is Cons<T> -> loop(nn - 1, l.tail)
        else -> Nil
      }
    return loop(n, this)
  }

  fun dropWhile(f: (T) -> Boolean): List<T> {
    tailrec fun loop(l: List<T>): List<T> =
      when (l) {
        is Nil -> Nil
        is Cons<T> ->
          if (f(l.head)) loop(l.tail)
          else l
      }
    return loop(this)
  }

  fun <B> foldLeft(init: B, f: (B, T) -> B): B {
    tailrec fun loop(l: List<T>, acc: B): B =
      when (l) {
        is Cons<T> -> loop(l.tail, f(acc, l.head))
        else -> acc
      }
    return loop(this, init)
  }

  fun reverse(): List<T> =
    foldLeft(Nil as List<T>) { acc, x -> Cons(x, acc) }

  fun <B> foldRight(z: B, f: (T, B) -> B): B =
    reverse().foldLeft(z) { acc, x -> f(x, acc) }

  fun <B> map(f: (T) -> B): List<B> =
    foldRight(Nil as List<B>) { x, acc -> Cons(f(x), acc) }

  fun init(): List<T> = when (this) {
    is Cons<T> ->
      if (this.tail === Nil) Nil
      else Cons(this.head, this.tail.init())
    else -> Nil
  }

  override fun toString(): String {
    tailrec fun loop(delimiter: String, s: StringBuilder, l: List<T>): String =
      when (l) {
        is Nil -> s.toString()
        is Cons<T> -> {
          s.append(delimiter)
          s.append(l.head)
          loop(",", s, l.tail)
        }
      }

    val list = loop("", StringBuilder(), this)
    return "[$list]"
  }

  fun tail(): List<T> = when (this) {
    is Nil -> Nil
    is Cons<T> -> this.tail
  }

  override fun equals(other: Any?): Boolean {
    if (other === null) return false
    if (other !is List<*>) return false
    tailrec fun loop(self: List<T>, other: List<*>): Boolean =
      when (self) {
        is Cons<T> -> when (other) {
          is Cons<*> ->
            if (self.head != other.head) false
            else loop(self.tail, other.tail)
          else -> false
        }
        else -> other is Nil
      }
    return loop(this, other)
  }

  override fun hashCode(): Int {
    if (hashCode == 0) {
      fun loop(l: List<T>, result: Int): Int =
        when (l) {
          is Cons<T> -> {
            val headHashCode = l.head?.hashCode() ?: 1
            loop(l.tail, 31 * result + headHashCode)
          }
          else -> result
        }
      hashCode = loop(this, 1)
    }
    return hashCode
  }

  companion object {
    operator fun <T> invoke(vararg data: T): List<T> {
      tailrec fun loop(acc: List<T>, index: Int): List<T> =
        if (index < 0) acc
        else loop(Cons(data[index], acc), index - 1)
      return loop(Nil, data.size - 1)
    }

    fun <T> reverse(vararg data: T): List<T> {
      tailrec fun loop(acc: List<T>, index: Int): List<T> =
        if (index >= data.size) acc
        else loop(Cons(data[index], acc), index + 1)
      return loop(Nil, 0)
    }
  }
}

fun <T> List<T>.setHead(head: T): List<T> = when (this) {
  is List.Nil -> List.Nil
  is List.Cons -> List.Cons(head, this.tail)
}

fun <T> List<T>.appendLeft(l: List<T>): List<T> =
  l.foldRight(this) { x, acc -> List.Cons(x, acc) }

fun <T> List<T>.appendRight(l: List<T>): List<T> =
  this.foldRight(l) { x, acc -> List.Cons(x, acc) }

fun <T> List<T>.appendLeft(x: T): List<T> = List.Cons(x, this)

operator fun <T> List<T>.plus(x: T): List<T> = this.appendLeft(x)

fun <T> List<T>.concatenate(m: Monoid<T>) = foldLeft(m.zero()) { acc, x -> m.op(acc, x)}
