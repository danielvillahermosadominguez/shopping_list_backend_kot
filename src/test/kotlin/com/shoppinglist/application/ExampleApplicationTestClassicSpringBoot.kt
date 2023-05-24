package com.shoppinglist.application

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExampleApplicationTestClassicSpringBoot {
    @Test
    fun it_should_be_true_example_classic_springboot_appTest() {
        assertThat(true).isTrue
        true shouldBe true
    }

    @Test
    fun it_should_get_an_example_of_environment_variable() {
        // TO BE DEVELOPED
        true shouldNotBe false
    }
}
