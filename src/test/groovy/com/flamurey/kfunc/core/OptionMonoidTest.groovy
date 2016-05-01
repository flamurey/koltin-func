package com.flamurey.kfunc.core

class OptionMonoidTest extends MonoidTest<Option<Integer>> {
    def setupSpec() {
        x = Option.@Companion.invoke(10)
        y = Option.@Companion.invoke(11)
        z = Option.@Companion.invoke()

        def factory = Monoid.@Factory
        monoids = [factory.getOptionMonoid(factory.intAddition),
                   factory.getOptionMonoid(factory.intMultiplication)]
    }
}
