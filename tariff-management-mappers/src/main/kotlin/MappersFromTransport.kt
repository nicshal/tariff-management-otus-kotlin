package ru.nicshal.tariff.management.mappers

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import ru.nicshal.tariff.management.api.v1.models.*
import ru.nicshal.tariff.management.common.NONE
import ru.nicshal.tariff.management.common.TrmngContext
import ru.nicshal.tariff.management.common.TrmngRequestId
import ru.nicshal.tariff.management.common.models.*
import ru.nicshal.tariff.management.common.stubs.TrmngStubs
import ru.nicshal.tariff.management.mappers.exceptions.UnknownRequestClass

fun TrmngContext.fromTransport(request: IRequest) = when (request) {
    is TariffCreateRequest -> fromTransport(request)
    is TariffReadRequest -> fromTransport(request)
    is TariffUpdateRequest -> fromTransport(request)
    is TariffDeleteRequest -> fromTransport(request)
    is TariffSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toTariffId() = this?.let { TrmngTariffId(it) } ?: TrmngTariffId.NONE
private fun String?.toTariffDate() = this?.let { LocalDate.parse(it).atStartOfDayIn(TimeZone.UTC) } ?: Instant.NONE
private fun String?.toTariffWithId() = TrmngTariff(id = this.toTariffId())
private fun IRequest?.requestId() = this?.requestId?.let { TrmngRequestId(it) } ?: TrmngRequestId.NONE

private fun TariffDebug?.transportToWorkMode(): TrmngWorkModes = when (this?.mode) {
    TariffRequestDebugModes.PROD -> TrmngWorkModes.PROD
    TariffRequestDebugModes.TEST -> TrmngWorkModes.TEST
    TariffRequestDebugModes.STUB -> TrmngWorkModes.STUB
    null -> TrmngWorkModes.PROD
}

private fun TariffDebug?.transportToStubCase(): TrmngStubs = when (this?.stub) {
    TariffRequestDebugStubs.SUCCESS -> TrmngStubs.SUCCESS
    TariffRequestDebugStubs.NOT_FOUND -> TrmngStubs.NOT_FOUND
    TariffRequestDebugStubs.BAD_ID -> TrmngStubs.BAD_ID
    TariffRequestDebugStubs.BAD_SERVICE_TYPE_CODE -> TrmngStubs.BAD_SERVICE_TYPE_CODE
    TariffRequestDebugStubs.BAD_TARIFF_TYPE_CODE -> TrmngStubs.BAD_TARIFF_TYPE_CODE
    TariffRequestDebugStubs.BAD_STATUS -> TrmngStubs.BAD_STATUS
    TariffRequestDebugStubs.BAD_BEGIN_DATE -> TrmngStubs.BAD_BEGIN_DATE
    TariffRequestDebugStubs.BAD_DESCRIPTION -> TrmngStubs.BAD_DESCRIPTION
    TariffRequestDebugStubs.BAD_END_DATE -> TrmngStubs.BAD_END_DATE
    TariffRequestDebugStubs.CANNOT_DELETE -> TrmngStubs.CANNOT_DELETE
    TariffRequestDebugStubs.BAD_SEARCH_STRING -> TrmngStubs.BAD_SEARCH_STRING
    null -> TrmngStubs.NONE
}

fun TrmngContext.fromTransport(request: TariffCreateRequest) {
    command = TrmngCommand.CREATE
    requestId = request.requestId()
    tariffRequest = request.tariff?.toInternal() ?: TrmngTariff()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TrmngContext.fromTransport(request: TariffReadRequest) {
    command = TrmngCommand.READ
    requestId = request.requestId()
    tariffRequest = request.tariff?.id.toTariffWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TrmngContext.fromTransport(request: TariffUpdateRequest) {
    command = TrmngCommand.UPDATE
    requestId = request.requestId()
    tariffRequest = request.tariff?.toInternal() ?: TrmngTariff()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TrmngContext.fromTransport(request: TariffDeleteRequest) {
    command = TrmngCommand.DELETE
    requestId = request.requestId()
    tariffRequest = request.tariff?.id.toTariffWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun TrmngContext.fromTransport(request: TariffSearchRequest) {
    command = TrmngCommand.SEARCH
    requestId = request.requestId()
    tariffFilterRequest = request.tariffFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun TariffSearchFilter?.toInternal(): TrmngTariffFilter = TrmngTariffFilter(
    searchString = this?.searchString ?: ""
)

private fun TariffCreateObject.toInternal(): TrmngTariff = TrmngTariff(
    tariffCode = this.tariffCode ?: "",
    tariffTypeCode = this.tariffTypeCode.fromTransport(),
    serviceTypeCode = this.serviceTypeCode.fromTransport(),
    description = this.description ?: "",
    status = this.status.fromTransport(),
    beginDate = this.beginDate.toTariffDate(),
    endDate = this.endDate.toTariffDate(),
)

private fun TariffUpdateObject.toInternal(): TrmngTariff = TrmngTariff(
    id = this.id.toTariffId(),
    tariffCode = this.tariffCode ?: "",
    tariffTypeCode = this.tariffTypeCode.fromTransport(),
    serviceTypeCode = this.serviceTypeCode.fromTransport(),
    description = this.description ?: "",
    status = this.status.fromTransport(),
    beginDate = this.beginDate.toTariffDate(),
    endDate = this.endDate.toTariffDate(),
)

private fun ServiceTypes?.fromTransport(): TrmngServiceTypes = when (this) {
    ServiceTypes.OPEN_ACCOUNT -> TrmngServiceTypes.OPEN_ACCOUNT
    ServiceTypes.CLOSE_ACCOUNT -> TrmngServiceTypes.CLOSE_ACCOUNT
    ServiceTypes.KEEP_ACCOUNT -> TrmngServiceTypes.KEEP_ACCOUNT
    null -> TrmngServiceTypes.NONE
}

private fun TariffTypes?.fromTransport(): TrmngTariffTypes = when (this) {
    TariffTypes.INDIVIDUAL -> TrmngTariffTypes.INDIVIDUAL
    TariffTypes.STANDART -> TrmngTariffTypes.STANDART
    null -> TrmngTariffTypes.NONE
}

private fun TariffStatuses?.fromTransport(): TrmngStatuses = when (this) {
    TariffStatuses.PROJECT -> TrmngStatuses.PROJECT
    TariffStatuses.ACTIVE -> TrmngStatuses.ACTIVE
    null -> TrmngStatuses.NONE
}

