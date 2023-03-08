package ru.nicshal.tariff.management.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class TrmngTariffId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = TrmngTariffId("")
    }
}
