package com.shoppinglist.application

import org.json.JSONException
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.Base64Utils
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets.UTF_8

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

    private val addAllLocation: String = """
         mutation {
            addLocation(
                id:"1",
                name:"name4",
                address:"address4") {
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
        val query: String? = toJSON("query", findAllLocations)
        webTestClient!!.post()
            .uri(GRAPHQL_PATH)
            .header(
                "Authorization",
                "Basic " + Base64Utils
                    .encodeToString(("admin:admin").toByteArray(UTF_8)),
            )
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just<Any>(query!!), String::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.data.findAllLocations").isNotEmpty
            .jsonPath("$.data.findAllLocations[0].id").isEqualTo("1")
            .jsonPath("$.data.findAllLocations[0].name").isEqualTo("name1")
            .jsonPath("$.data.findAllLocations[0].address").isEqualTo("address1")
            .jsonPath("$.data.findAllLocations").isNotEmpty
            .jsonPath("$.data.findAllLocations[1].id").isEqualTo("2")
            .jsonPath("$.data.findAllLocations[1].name").isEqualTo("name2")
            .jsonPath("$.data.findAllLocations[1].address").isEqualTo("address2")
            .jsonPath("$.data.findAllLocations[2].id").isEqualTo("3")
            .jsonPath("$.data.findAllLocations[2].name").isEqualTo("name3")
            .jsonPath("$.data.findAllLocations[2].address").isEqualTo("address3")
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

    @Test
    fun `add location`() {
        val query: String? = toJSON("query", addAllLocation)
        var result = webTestClient!!.post()
            .uri(GRAPHQL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just<Any>(query!!), String::class.java)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .json(
                """
           {
              "data": {
                "addLocation": {
                  "id": "1",
                  "name": "name4",
                  "address": "address4"
                }
              }
           }
                """.trimIndent(),
            )
        // .jsonPath("$.data.addAllLocation").isNotEmpty

        // You are returned the following json
    }

    private fun toJSON(fieldName: String, queryMutation: String): String? {
        return try {
            JSONObject().put(fieldName, queryMutation).toString()
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }
    }
}
