package com.flamurey.kfunc.core

interface Monad<out A> {
  fun get(): A
  fun isPresent(): Boolean
  fun <B> map(f: (A) -> B): Monad<B>
  fun <B> flatMap(f: (A) -> Monad<B>): Monad<B>
}

fun <A, B> Monad<Pair<A, B>>.distribute(): Pair<Monad<A>, Monad<B>> =
  this.map { it.first } to this.map { it.second }

interface IO<out A> : Monad<A> {
  override fun isPresent(): Boolean = true

  override fun <B> map(f: (A) -> B): Monad<B> = object : IO<B> {
    override fun get(): B = f(this@IO.get())
  }

  override fun <B> flatMap(f: (A) -> Monad<B>): Monad<B> = object : IO<B> {
    override fun get(): B = f(this@IO.get()).get()
  }
}

class ReadLine: IO<String> {
  override fun get(): String = readLine() ?: ""
}

class PrintLine(val value: String): IO<Unit> {
  override fun get() = println(value)
}