package ru.nicshal.tariff.management.mappers

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import org.junit.Test
import ru.nicshal.tariff.management.api.v1.models.*
import ru.nicshal.tariff.management.common.TrmngContext
import ru.nicshal.tariff.management.common.TrmngRequestId
import ru.nicshal.tariff.management.common.models.*
import ru.nicshal.tariff.management.common.stubs.TrmngStubs
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = TariffCreateRequest(
            requestId = "1234",
            debug = TariffDebug(
                mode = TariffRequestDebugModes.STUB,
                stub = TariffRequestDebugStubs.SUCCESS,
            ),
            tariff = TariffCreateObject(
                tariffCode = "standart_1",
                tariffTypeCode = TariffTypes.STANDART,
                serviceTypeCode = ServiceTypes.CLOSE_ACCOUNT,
                description = "desc",
                status = TariffStatuses.PROJECT,
                beginDate = "2023-01-12",
                endDate = "2024-01-18",
            ),
        )

        val context = TrmngContext()
        context.fromTransport(req)

        assertEquals(TrmngStubs.SUCCESS, context.stubCase)
        assertEquals(TrmngWorkModes.STUB, context.workMode)
        assertEquals("desc", context.tariffRequest.description)
        assertEquals(TrmngStatuses.PROJECT, context.tariffRequest.status)
        assertEquals(TrmngTariffTypes.STANDART, context.tariffRequest.tariffTypeCode)
    }

    @Test
    fun toTransport() {
        val context = TrmngContext(
            requestId = TrmngRequestId("1234"),
            command = TrmngCommand.CREATE,
            tariffResponse = TrmngTariff(
                tariffCode = "standart_1",
                tariffTypeCode = TrmngTariffTypes.STANDART,
                serviceTypeCode = TrmngServiceTypes.CLOSE_ACCOUNT,
                description = "desc",
                status = TrmngStatuses.PROJECT,
                beginDate = LocalDate.parse("2022-05-09").atStartOfDayIn(TimeZone.UTC),
                endDate = LocalDate.parse("2024-05-09").atStartOfDayIn(TimeZone.UTC),
            ),
            errors = mutableListOf(
                TrmngError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = TrmngStates.RUNNING,
        )

        val req = context.toTransportTariff() as TariffCreateResponse

        assertEquals("1234", req.requestId)
        assertEquals("standart_1", req.tariff?.tariffCode)
        assertEquals("desc", req.tariff?.description)
        assertEquals(TariffTypes.STANDART, req.tariff?.tariffTypeCode)
        assertEquals(TariffStatuses.PROJECT, req.tariff?.status)
        assertEquals("2022-05-09", req.tariff?.beginDate)
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}