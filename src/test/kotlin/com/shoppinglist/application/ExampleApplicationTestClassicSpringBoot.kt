package com.shoppinglist.application

import com.shoppinglist.configuration.AppConfiguration
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
class ExampleApplicationTestClassicSpringBoot {
    @Autowired
    private val appConfiguration: AppConfiguration? = null

    @Test
    fun it_should_be_true_example_classic_springboot_appTest() {
        assertThat(true).isTrue
        true shouldBe true
    }

    @Test
    fun it_should_get_an_example_of_environment_variable() {
        // TO BE DEVELOPED
        appConfiguration?.getExampleVariable() shouldBe "Example"
    }
}
