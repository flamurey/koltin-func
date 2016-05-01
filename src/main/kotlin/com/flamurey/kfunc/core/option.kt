package com.flamurey.kfunc.core

sealed class Option<out A> : Monada<A> {

  internal object None : Option<Nothing>() {
    override fun get(): Nothing {
      throw UnsupportedOperationException()
    }

    override fun isPresent(): Boolean = false

    override fun <B> flatMap(f: (Nothing) -> Monada<B>): Monada<B>  = None

    override fun <B> map(f: (Nothing) -> B) = None

    override fun toString(): String {
      return "Option()"
    }

    override fun equals(other: Any?): Boolean {
      return other !== null && other is None
    }

    override fun hashCode() = 1
  }

  internal class Some<out A>(val get: A) : Option<A>() {
    override fun get(): A = get

    override fun isPresent(): Boolean  = true

    override fun <B> map(f: (A) -> B): Option<B> = Some(f(get))

    override fun <B> flatMap(f: (A) -> Monada<B>): Option<B> {
      val m = f(get)
      return when (m) {
        is Option<B> -> m
        else ->
          if (m.isPresent()) Some(m.get())
          else None
      }
    }

    override fun toString(): String {
      return "Option($get)"
    }

    override fun equals(other: Any?): Boolean {
      return other !== null && other is Some<*> && other.get == get
    }

    override fun hashCode() = this.get?.hashCode() ?: 1
  }

  fun filter(f: (A) -> Boolean): Option<A> =
    when (this) {
      is Some<A> -> if (f(get)) this else None
      else -> None
    }

  companion object {
    operator fun <A> invoke(a: A): Option<A> = Some(a)

    operator fun <A> invoke(): Option<A> = None
  }
}

fun <A> Option<A>.getOrElse(other: A): A =
  when (this) {
    is Option.Some<A> -> get
    else -> other
  }

fun <A> Option<A>.orElse(other: A): Option<A> =
  when (this) {
    is Option.Some<A> -> this
    else -> Option(other)
  }
