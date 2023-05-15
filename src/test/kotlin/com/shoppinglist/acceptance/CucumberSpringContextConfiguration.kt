package com.shoppinglist.acceptance

import com.shoppinglist.application.ShoppingListBackendKotApplication
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest

@CucumberContextConfiguration
@SpringBootTest(classes = [(ShoppingListBackendKotApplication::class)])
class CucumberSpringContextConfiguration
