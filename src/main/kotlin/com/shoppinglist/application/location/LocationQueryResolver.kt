package com.shoppinglist.application.location

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class LocationQueryResolver {
    @QueryMapping
    fun findAllLocations(): List<DummyLocation> {
        return listOf(
            DummyLocation("1", "name1", "address1"),
            DummyLocation("2", "name2", "address2"),
            DummyLocation("3", "name3", "address3"),
        )
    }
}
