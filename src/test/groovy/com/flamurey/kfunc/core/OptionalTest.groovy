package com.flamurey.kfunc.core

class OptionalTest extends MonoidTest<Optional<Integer>> {
    def setupSpec() {
        x = Optional.of(10)
        y = Optional.of(11)
        z = Optional.empty()

        def factory = Monoid.@Factory
        monoids = [factory.getOptionMonoid(factory.intAddition),
                   factory.getOptionMonoid(factory.intMultiplication)]
    }
}
