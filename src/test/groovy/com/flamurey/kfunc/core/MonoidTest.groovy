package com.flamurey.kfunc.core

import spock.lang.Shared
import spock.lang.Specification

abstract class MonoidTest<T> extends Specification {

    @Shared protected T x
    @Shared protected T y
    @Shared protected T z

    @Shared List<Monoid<T>> monoids

    def "test associative low"() {
        expect:
        monoid.op(monoid.op(x,y), z) == monoid.op(x, monoid.op(y, z))

        where:
        monoid << monoids
    }

    def "test identity element"() {
        expect:
        monoid.op(monoid.zero(), x) == x
        monoid.op(x, monoid.zero()) == x

        where:
        monoid << monoids
    }
}
