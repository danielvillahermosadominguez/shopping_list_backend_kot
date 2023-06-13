package com.shoppinglist.application

import org.json.JSONException
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class GraphQLExampleTest {
    private val GRAPHQL_PATH = "/graphql"
    private val findAllLocations: String = """
        {
            findAllLocations {
                id
                name
                address
            } 
        }
    """.trimIndent()

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `get all locations`() {
        val query: String? = toJSON(findAllLocations)
        webTestClient!!.post()
            .uri(GRAPHQL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just<Any>(query!!), String::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data.findAllLocations").isNotEmpty
        // You are returned the following json
        """
            {
              "data": {
                "findAllLocations": [
                  {
                    "id": "1",
                    "name": "name1",
                    "address": "address1"
                  },
                  {
                    "id": "2",
                    "name": "name2",
                    "address": "address2"
                  }
                ]
              }
            }
        """.trimIndent()
    }

    private fun toJSON(query: String): String? {
        return try {
            JSONObject().put("query", query).toString()
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }
    }
}
