package com.shoppinglist.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.shoppinglist.application", "com.shoppinglist.configuration"])
class ShoppingListBackendKotApplication

fun main(args: Array<String>) {
    runApplication<ShoppingListBackendKotApplication>(*args)
}
