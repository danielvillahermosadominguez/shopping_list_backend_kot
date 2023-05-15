package com.shoppinglist.application

import io.kotest.matchers.shouldBe
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
}
