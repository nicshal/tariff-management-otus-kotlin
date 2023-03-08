package ru.nicshal.tariff.management.common

import kotlin.jvm.JvmInline

@JvmInline
value class TrmngRequestId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TrmngRequestId("")
    }
}
