package com.troy.playground.piccollagequiz1

class Containing7s {

    //test case in Containing7sUnitTest.kt
    fun containing(n : Int) : Int{
        if(n <= 9) {
            return if (n >= 7) { 1 } else { 0 }
        } else {
            val head = getHead(n) // head must >= 1
            val body = getBody(n)

            if (head < 7) {
                return (head) * containing(convertTo9(n)) + containing(body)
            } else if (head == 7) {
                return (head) * containing(convertTo9(n)) + body + 1
            } else {
                return (head - 1) * containing(convertTo9(n)) + containing(body) + convertTo9(n) + 1
            }
        }
    }

//    right answer but bad performance on big number
//
//    private fun containing ( n : Int) : Int{
//        var counter = 0
//        for (i in 1..n) {
//            if (i.toString().contains("7")) {
//                counter++
//            }
//        }
//
//        return  counter
//    }

    private fun getHead(n : Int) : Int {
        return Character.getNumericValue(n.toString()[0])
    }

    private fun getBody(n : Int) : Int {
        return n.toString().subSequence(1, n.toString().length).toString().toInt()
    }

    //input a length n digit , out put a n-1 length 9...9 digit
    private fun convertTo9(n : Int) : Int {

        var num = 0

        for (i in 1 until(n.toString().length)) {
            num = num * 10 + 9
        }
        return num
    }

}