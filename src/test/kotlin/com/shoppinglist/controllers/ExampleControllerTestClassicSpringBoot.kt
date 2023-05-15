package com.shoppinglist.controllers

import com.ninjasquad.springmockk.MockkBean
import com.shoppinglist.application.ShoppingListBackendKotApplication
import com.shoppinglist.application.dummy.DummyEntity
import com.shoppinglist.application.dummy.DummyService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ContextConfiguration(classes = [(ShoppingListBackendKotApplication::class)])
class ExampleControllerTestClassicSpringBoot(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var dummyService: DummyService

    @WithMockUser(value = "spring")
    @Test
    fun this_is_an_example_with_mvc() {
        every { dummyService.getAll() } returns listOf(DummyEntity())
        mockMvc.perform(get("http://localhost:8080//api/dummy"))
            .andExpect(status().isOk)
    }
}
