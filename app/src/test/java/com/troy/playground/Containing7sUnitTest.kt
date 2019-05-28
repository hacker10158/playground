package com.troy.playground

import com.troy.playground.piccollagequiz1.Containing7s
import org.junit.Assert
import org.junit.Test

class Containing7sUnitTest {

    @Test
    fun testContaining7s() {
        val target = Containing7s()
        val testValue1 = target.containing(5)
        val testValue2 = target.containing(7)
        val testValue3 = target.containing(18)
        val testValue4 = target.containing(100)
        val testValue5 = target.containing(1000)
        val testValue6 = target.containing(2468)
        val testValue7 = target.containing(123456)

        Assert.assertEquals(0, testValue1)
        Assert.assertEquals(1, testValue2)
        Assert.assertEquals(2, testValue3)
        Assert.assertEquals(19, testValue4)
        Assert.assertEquals(271, testValue5)
        Assert.assertEquals(625, testValue6)
        Assert.assertEquals(48723, testValue7)
    }
}