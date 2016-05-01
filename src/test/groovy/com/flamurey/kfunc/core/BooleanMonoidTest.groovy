package com.flamurey.kfunc.core

class BooleanMonoidTest extends MonoidTest<Boolean> {
    def setupSpec() {
        x = true
        y = false
        z = true

        def factory = Monoid.@Factory
        monoids = [factory.booleanAnd, factory.booleanOr]
    }
}
