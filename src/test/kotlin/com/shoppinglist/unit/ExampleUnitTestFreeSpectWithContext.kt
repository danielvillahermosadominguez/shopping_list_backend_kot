package com.shoppinglist.unit

import io.kotest.core.spec.style.FreeSpec
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
class ExampleUnitTestFreeSpectWithContext : FreeSpec({
    "this is an example of test" {
        assert(true)
    }
})
