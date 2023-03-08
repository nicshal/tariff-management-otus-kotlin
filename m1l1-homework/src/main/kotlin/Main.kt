package ru.nicshal.marketplace.m1l1

import kotlinx.datetime.*

fun main() {
    println("Hello World!")

    val test: Instant = LocalDate.parse("2022-05-09").atStartOfDayIn(TimeZone.UTC)
    println(test.toLocalDateTime(TimeZone.UTC).date.toString())
}
