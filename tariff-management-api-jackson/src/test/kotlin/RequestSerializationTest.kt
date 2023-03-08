package ru.nicshal.tariff.management.api.v1

import ru.nicshal.tariff.management.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val request = TariffCreateRequest(
        requestId = "123",
        debug = TariffDebug(
            mode = TariffRequestDebugModes.STUB,
            stub = TariffRequestDebugStubs.BAD_DESCRIPTION
        ),
        tariff = TariffCreateObject(
            tariffCode = "tariff code",
            tariffTypeCode = TariffTypes.INDIVIDUAL,
            serviceTypeCode = ServiceTypes.OPEN_ACCOUNT,
            description = "tariff description",
            status = TariffStatuses.PROJECT,
            beginDate = "",
            endDate = "",
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"description\":\\s*\"tariff description\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badDescription\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as TariffCreateRequest

        assertEquals(request, obj)
    }
}
