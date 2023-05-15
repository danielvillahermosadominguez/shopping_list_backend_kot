package com.shoppinglist.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShoppingListBackendKotApplication

fun main(args: Array<String>) {
    runApplication<ShoppingListBackendKotApplication>(*args)
}
