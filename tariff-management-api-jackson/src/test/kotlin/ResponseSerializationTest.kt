package ru.nicshal.tariff.management.api.v1

import ru.nicshal.tariff.management.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = TariffCreateResponse(
        requestId = "123",
        tariff = TariffResponseObject(
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
        val json = apiV1Mapper.writeValueAsString(response)

        assertContains(json, Regex("\"description\":\\s*\"tariff description\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(response)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as TariffCreateResponse

        assertEquals(response, obj)
    }
}