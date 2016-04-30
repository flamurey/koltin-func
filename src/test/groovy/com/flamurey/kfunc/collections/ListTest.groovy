package com.flamurey.kfunc.collections

import spock.lang.Specification

class ListTest extends Specification {
    static listUtil = List["Companion"] as List.Companion

    def "Drop"() {
        expect:
        list(2, 4).drop(1) == list(4)
    }

    def "DropWhile"() {
        expect:
        list(1, 2, 4).dropWhile { it < 3} == list(4)
    }

    def "FoldLeft"() {
        expect:
        list(1, 2, 3).foldLeft("") { acc, x -> acc + x } == "123"
    }

    def "Reverse"() {
        expect:
        list(1, 2, 3).reverse() == list(3, 2, 1)
    }

    def "FoldRight"() {
        expect:
        list(1, 2, 3).foldRight("") { x, acc -> acc + x } == "321"
    }

    def "map"() {
        expect:
        list(1, 2).map { it * 2 } == list(2, 4)
    }

    def "init"() {
        expect:
        list(1, 2, 3).init() == list(1, 2)
    }

    def "test toString"() {
        expect:
        list(1, 2, 3).toString() == "[1,2,3]"
    }

    def "tail"() {
        expect:
        list(1, 2).tail() == list(2)
    }

    def "set new head"() {
        expect:
        list(1, 2).setHead(2) == list(2, 2)
    }

    def "append new item"() {
        expect:
        list(2, 3).appendLeft(1) == list(1, 2, 3)
    }

    def "append new list into left"() {
        expect:
        list(2, 3).appendLeft(list(0, 1)) == list(0, 1, 2, 3)
    }

    def "append new list into right"() {
        expect:
        list(2, 3).appendRight(list(0, 1)) == list(2, 3, 0, 1)
    }

    def "create reversed list"() {
        expect:
        reversedList(2, 3) == list(2, 3).reverse()
    }

    static <T> List<T> list(T... data) {
        return listUtil.invoke(data)
    }

    static <T> List<T> reversedList(T... data) {
        return listUtil.reverse(data)
    }
}
