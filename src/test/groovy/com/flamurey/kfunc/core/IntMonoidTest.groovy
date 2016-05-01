package com.flamurey.kfunc.core

class IntMonoidTest extends MonoidTest<Integer> {
    def setupSpec() {
        x = 10
        y = 11
        z = 4

        def factory = Monoid.@Factory
        monoids = [factory.intAddition, factory.intMultiplication]
    }
}
