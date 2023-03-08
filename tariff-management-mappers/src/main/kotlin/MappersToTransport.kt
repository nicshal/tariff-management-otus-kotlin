package ru.nicshal.tariff.management.mappers

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.nicshal.tariff.management.api.v1.models.*
import ru.nicshal.tariff.management.common.TrmngContext
import ru.nicshal.tariff.management.common.models.*
import ru.nicshal.tariff.management.mappers.exceptions.UnknownTrmngCommand

fun TrmngContext.toTransportTariff(): IResponse = when (val cmd = command) {
    TrmngCommand.CREATE -> toTransportCreate()
    TrmngCommand.READ -> toTransportRead()
    TrmngCommand.UPDATE -> toTransportUpdate()
    TrmngCommand.DELETE -> toTransportDelete()
    TrmngCommand.SEARCH -> toTransportSearch()
    TrmngCommand.NONE -> throw UnknownTrmngCommand(cmd)
}

fun TrmngContext.toTransportCreate() = TariffCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TrmngStates.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tariff = tariffResponse.toTransportTariff()
)

fun TrmngContext.toTransportRead() = TariffReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TrmngStates.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tariff = tariffResponse.toTransportTariff()
)

fun TrmngContext.toTransportUpdate() = TariffUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TrmngStates.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tariff = tariffResponse.toTransportTariff()
)

fun TrmngContext.toTransportDelete() = TariffDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TrmngStates.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tariff = tariffResponse.toTransportTariff()
)

fun TrmngContext.toTransportSearch() = TariffSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == TrmngStates.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    tariffs = tariffsResponse.toTransportTariff()
)

fun List<TrmngTariff>.toTransportTariff(): List<TariffResponseObject>? = this
    .map { it.toTransportTariff() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun TrmngTariff.toTransportTariff(): TariffResponseObject = TariffResponseObject(
    id = id.takeIf { it != TrmngTariffId.NONE }?.asString(),
    tariffCode = tariffCode.takeIf { it.isNotBlank() },
    tariffTypeCode = tariffTypeCode.toTransportTariff(),
    serviceTypeCode = serviceTypeCode.toTransportTariff(),
    description = description.takeIf { it.isNotBlank() },
    status = status.toTransportTariff(),
    beginDate = beginDate.toLocalDateTime(TimeZone.UTC).date.toString(),
    endDate = endDate.toLocalDateTime(TimeZone.UTC).date.toString(),
    ownerId = ownerId.takeIf { it != TrmngUserId.NONE }?.asString(),
    permissions = permissionsClient.toTransportTariff(),
)

private fun Set<TrmngTariffPermissionClient>.toTransportTariff(): Set<TariffPermissions>? = this
    .map { it.toTransportTariff() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun TrmngTariffPermissionClient.toTransportTariff() = when (this) {
    TrmngTariffPermissionClient.READ -> TariffPermissions.READ
    TrmngTariffPermissionClient.UPDATE -> TariffPermissions.UPDATE
    TrmngTariffPermissionClient.MAKE_ACTIVE -> TariffPermissions.MAKE_ACTIVE
    TrmngTariffPermissionClient.DELETE -> TariffPermissions.DELETE
}

private fun TrmngTariffTypes.toTransportTariff(): TariffTypes? = when (this) {
    TrmngTariffTypes.STANDART -> TariffTypes.STANDART
    TrmngTariffTypes.INDIVIDUAL -> TariffTypes.INDIVIDUAL
    TrmngTariffTypes.NONE -> null
}

private fun TrmngStatuses.toTransportTariff(): TariffStatuses? = when (this) {
    TrmngStatuses.ACTIVE -> TariffStatuses.ACTIVE
    TrmngStatuses.PROJECT -> TariffStatuses.PROJECT
    TrmngStatuses.NONE -> null
}

private fun TrmngServiceTypes.toTransportTariff(): ServiceTypes? = when (this) {
    TrmngServiceTypes.KEEP_ACCOUNT -> ServiceTypes.KEEP_ACCOUNT
    TrmngServiceTypes.OPEN_ACCOUNT -> ServiceTypes.OPEN_ACCOUNT
    TrmngServiceTypes.CLOSE_ACCOUNT -> ServiceTypes.CLOSE_ACCOUNT
    TrmngServiceTypes.NONE -> null
}

private fun List<TrmngError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportTariff() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun TrmngError.toTransportTariff() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)