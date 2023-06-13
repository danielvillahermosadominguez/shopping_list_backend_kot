package com.shoppinglist.application.location

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller

@Controller
class LocationMutationResolver {
    @MutationMapping
    fun addLocation(@Argument id: String, @Argument name: String, @Argument address: String): DummyLocation {
        return DummyLocation(id, name, address)
    }
}
