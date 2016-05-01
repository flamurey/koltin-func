package com.flamurey.kfunc.core

sealed class Either<out A, out B> : Monada<B> {
  class Left<out A>(val value: A) : Either<A, Nothing>() {
    override fun get(): Nothing {
      throw UnsupportedOperationException()
    }

    override fun isPresent(): Boolean = false

    override fun <B> map(f: (Nothing) -> B): Either<A, B> = this

    override fun <B> flatMap(f: (Nothing) -> Monada<B>): Either<A, B> = this
  }

  class Right<out B>(val value: B) : Either<Nothing, B>() {
    override fun get(): B = value

    override fun isPresent(): Boolean = true

    override fun <C> map(f: (B) -> C): Either<Nothing, C> = Right(f(value))

    override fun <C> flatMap(f: (B) -> Monada<C>): Either<Monada<C>, C> {
      val m = f(value)
      return when (m) {
        is Right<C> -> m
        else ->
          if (m.isPresent()) Right(m.get())
          else Left(m)
      }
    }
  }

  fun apply(success: (B) -> Unit, fail: (A) -> Unit) : Unit =
    when (this) {
      is Either.Left<A> -> fail(value)
      is Either.Right<B> -> success(value)
    }

  companion object {
    fun <A, B> fail(error: A): Either<A, B> = Left(error)
    fun <A, B> success(result: B): Either<A, B> = Right(result)
  }
}

fun <A, B, C> Either<A, B>.flatMap(f: (B) -> Either<A, C>): Either<A, C> =
  when (this) {
    is Either.Left<A> -> this
    is Either.Right<B> -> f(value)
  }

fun <E, A, B, C> Either<E, A>.map2(other: Either<E, B>, f: (A, B) -> C): Either<E, C> =
  when (this) {
    is Either.Left<E> -> this
    is Either.Right<A> -> when (other) {
      is Either.Left<E> -> other
      is Either.Right<B> -> Either.Right(f(this.value, other.value))
    }
  }

fun <A, B> Either<A, B>.getOrElse(other: B): B =
  when (this) {
    is Either.Right<B> -> value
    else -> other
  }

fun <A, B> Either<A, B>.orElse(other: B): Either<A, B> =
  when (this) {
    is Either.Right<B> -> this
    else -> Either.Right(other)
  }

fun <A> tryDo(f: () -> A): Either<Exception, A> =
  try {
    Either.Right(f())
  } catch (e: Exception) {
    Either.Left(e)
  }

fun <A, B> Either<Monada<A>, Monada<B>>.codistribute(): Monada<Either<A, B>> =
  when (this) {
    is Either.Left<Monada<A>> -> value.map { Either.Left(it) }
    is Either.Right<Monada<B>> -> value.map { Either.Right(it) }
  }
