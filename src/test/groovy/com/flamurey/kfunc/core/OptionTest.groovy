package com.flamurey.kfunc.core

import spock.lang.Specification

class OptionTest extends Specification {
    def "map"() {
        expect:
        option(2).map { it * 2 } == option(4)
        none().map { it * 2 } == none()
    }

    def "flatMap"() {
        expect:
        option(2).flatMap { none() } == none()
        none().flatMap { option(it * 2) } == none()
        option(2).flatMap { option(it * 2) } == option(4)
    }

    def "Filter"() {
        expect:
        option(2).filter { it > 1 } == option(2)
        option(1).filter { it > 1 } == none()
        none().map { it > 1 } == none()
    }

    def "getOrElse"() {
        expect:
        option(2).getOrElse(1) == 2
        none().getOrElse(1) == 1
    }

    def "orElse"() {
        expect:
        option(2).orElse(1) == option(2)
        none().orElse(1) == option(1)
    }

    static <T> Option<T> none() {
        return Option.@Companion.invoke()
    }

    static <T> Option<T> option(T a) {
        return Option.@Companion.invoke(a)
    }
}
