package ru.nicshal.tariff.management.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class TrmngUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TrmngUserId("")
    }
}