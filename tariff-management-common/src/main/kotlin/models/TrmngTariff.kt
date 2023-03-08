package ru.nicshal.tariff.management.common.models

import kotlinx.datetime.Instant
import ru.nicshal.tariff.management.common.NONE

data class TrmngTariff(
    var id: TrmngTariffId = TrmngTariffId.NONE,
    var tariffCode: String = "",
    var tariffTypeCode: TrmngTariffTypes = TrmngTariffTypes.NONE,
    var serviceTypeCode: TrmngServiceTypes = TrmngServiceTypes.NONE,
    var description: String = "",
    val status: TrmngStatuses = TrmngStatuses.NONE,
    var beginDate: Instant = Instant.NONE,
    var endDate: Instant = Instant.NONE,
    var ownerId: TrmngUserId = TrmngUserId.NONE,
    val permissionsClient: MutableSet<TrmngTariffPermissionClient> = mutableSetOf()
)