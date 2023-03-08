package ru.nicshal.tariff.management.common.models

data class TrmngError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)