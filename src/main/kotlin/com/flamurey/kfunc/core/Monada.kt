package com.flamurey.kfunc.core

interface Monada<out A> {
  fun get(): A
  fun isPresent(): Boolean
  fun <B> map(f: (A) -> B): Monada<B>
  fun <B> flatMap(f: (A) -> Monada<B>): Monada<B>
}

fun <A, B> Monada<Pair<A, B>>.distribute(): Pair<Monada<A>, Monada<B>> =
  this.map { it.first } to this.map { it.second }