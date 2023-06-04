package com.shoppinglist.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.client.WireMock.containing
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class ExampleWiremockWithoutSupportOfFrameworks {
    private lateinit var wireMockServer: WireMockServer
    private val restTemplate: RestTemplate = RestTemplate()
    private val mapper = ObjectMapper()

    @AfterEach
    fun afterEach() {
        wireMockServer.stop()
    }

    @BeforeEach
    fun beforeEach() {
        wireMockServer = WireMockServer(6000)
        wireMockServer.start()
        wireMockServer!!.resetRequests()
        configureFor("localhost", 6000)
    }

    @Test
    fun shouldVerifyAnURLGetForObject() {
        val params: MutableMap<String, String> = HashMap()
        stubFor(
            get(urlEqualTo("/dummyIntegration"))
                .willReturn(
                    aResponse()
                        .withBody("Welcome to this integration!")
                        .withStatus(200),
                ),
        )
        val response = restTemplate.getForObject(
            "http://localhost:6000//dummyIntegration",
            String::class.java,
            params,
        )

        assertThat(response).isEqualTo("Welcome to this integration!")

        wireMockServer!!.verify(
            1,
            getRequestedFor(
                urlEqualTo(
                    "/dummyIntegration",
                ),
            ),
        )
    }

    @Test
    fun shouldVerifyAnURLGetForEntity() {
        val params: MutableMap<String, String> = HashMap()
        val expectedEntity = MessageDummyEntity("Welcome to this integration!")
        val expectedBodyRequest = mapper.writeValueAsString(expectedEntity)
        stubFor(
            get(urlEqualTo("/dummyIntegration"))
                .willReturn(
                    aResponse()
                        .withBody(expectedBodyRequest)
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200),
                ),
        )

        val response: ResponseEntity<MessageDummyEntity> = restTemplate.getForEntity(
            "http://localhost:6000//dummyIntegration",
            MessageDummyEntity::class.java,
            params,
        )

        assertThat(response.statusCode.value()).isEqualTo(200)
        assertThat(response.body!!.message).isEqualTo("Welcome to this integration!")
        wireMockServer!!.verify(1, getRequestedFor(urlEqualTo("/dummyIntegration")))
    }

    @Test
    fun shouldVerifyNotCallsWithGetForObject() {
        wireMockServer!!.verify(0, getRequestedFor(urlEqualTo("/dummyIntegration")))
    }

    @Test
    fun shouldVerifyAnURLPostForObject() {
        stubFor(post(urlEqualTo("/dummyIntegration/1")).willReturn(okJson("{\"message\":\"Welcome to this integration!\"}")))
        val entityToSend: HashMap<String, String> = HashMap()
        entityToSend["value"] = "dummyValue"
        val response = restTemplate.postForObject(
            "http://localhost:6000//dummyIntegration/1",
            entityToSend,
            HashMap::class.java,
        )

        assertThat(response!!["message"]).isEqualTo("Welcome to this integration!")
        wireMockServer!!.verify(
            1,
            postRequestedFor(urlEqualTo("/dummyIntegration/1"))
                .withRequestBody(containing("\"value\":\"dummyValue\"")),
        )
    }

    @Test
    fun shouldVerifyAnURLPostForEntity() {
        val expectedEntity = MessageDummyEntity("Welcome to this integration!")
        val expectedBodyRequest = mapper.writeValueAsString(expectedEntity)
        stubFor(
            post(urlEqualTo("/dummyIntegration/1"))
                .willReturn(
                    aResponse()
                        .withBody(expectedBodyRequest)
                        .withHeader("Content-type", MediaType.APPLICATION_JSON_VALUE),
                ),
        )
        val entityToSend = InputDummyEntity("dummyValue")
        val response: ResponseEntity<MessageDummyEntity> = restTemplate.postForEntity(
            "http://localhost:6000//dummyIntegration/1",
            entityToSend,
            MessageDummyEntity::class.java,
        )

        assertThat(response.body!!.message).isEqualTo("Welcome to this integration!")
        wireMockServer!!.verify(
            1,
            postRequestedFor(urlEqualTo("/dummyIntegration/1"))
                .withRequestBody(containing(mapper.writeValueAsString(entityToSend))),
        )
    }
}

data class MessageDummyEntity(
    val message: String? = null,
)

data class InputDummyEntity(
    val value: String? = null,
)
