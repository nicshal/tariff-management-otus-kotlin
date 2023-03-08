package ru.nicshal.tariff.management.common.models

data class TrmngTariffFilter(
    var searchString: String = "",
    var serviceTypeCode: TrmngServiceTypes = TrmngServiceTypes.NONE,
)
