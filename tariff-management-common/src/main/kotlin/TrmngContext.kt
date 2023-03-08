package ru.nicshal.tariff.management.common

import kotlinx.datetime.Instant
import ru.nicshal.tariff.management.common.models.*
import ru.nicshal.tariff.management.common.stubs.TrmngStubs

data class TrmngContext(
    var command: TrmngCommand = TrmngCommand.NONE,
    var state: TrmngStates = TrmngStates.NONE,
    val errors: MutableList<TrmngError> = mutableListOf(),

    var workMode: TrmngWorkModes = TrmngWorkModes.PROD,
    var stubCase: TrmngStubs = TrmngStubs.NONE,

    var requestId: TrmngRequestId = TrmngRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var tariffRequest: TrmngTariff = TrmngTariff(),
    var tariffFilterRequest: TrmngTariffFilter = TrmngTariffFilter(),
    var tariffResponse: TrmngTariff = TrmngTariff(),
    var tariffsResponse: MutableList<TrmngTariff> = mutableListOf(),
)