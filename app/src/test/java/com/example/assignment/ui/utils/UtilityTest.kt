package com.example.assignment.ui.utils

import junit.framework.TestCase
import org.junit.Test

class UtilityTest : TestCase(){
    @Test
    fun testDateFormatSuccess1(){
        val input = "2022-08-09T00:44:49Z"
        val expected = "2022-08-09 06:14:49 AM"
        val output = Utility.getReadableDate(input)
        assertEquals(expected, output)
    }

    @Test
    fun testDateFormatSuccess2() {
        val input = "2022-08-08T13:00:30Z"
        val expected = "2022-08-08 06:30:30 PM"
        val output = Utility.getReadableDate(input)
        assertEquals(expected, output)
    }

    @Test
    fun testDateFormatFail1() {
        val input = "2022-08-08"
        val expected = "2022-08-08"
        val output = Utility.getReadableDate(input)
        assertEquals(expected, output)
    }

    @Test
    fun testDateFormatFail2() {
        val input = "08/08/2022"
        val expected = "08/08/2022"
        val output = Utility.getReadableDate(input)
        assertEquals(expected, output)
    }
}