package com.flamurey.kfunc.core

import kotlin.Pair

class ProductMonoidTest extends MonoidTest<Pair<Integer, String>> {
    def setupSpec() {
        x = new Pair(10, "12")
        y = new Pair(12, "3")
        z = new Pair(3, "43")

        def factory = Monoid.@Factory
        monoids = [factory.getProductMonoid(factory.intAddition, factory.stringMonoid)]
    }
}

