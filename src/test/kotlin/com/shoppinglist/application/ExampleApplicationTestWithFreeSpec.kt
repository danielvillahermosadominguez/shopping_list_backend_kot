package com.shoppinglist.application

import io.kotest.core.spec.style.FreeSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExampleApplicationTestWithFreeSpec : FreeSpec({
    "this is an example of application test with FreeSpecs" {
        assert(true)
    }
})