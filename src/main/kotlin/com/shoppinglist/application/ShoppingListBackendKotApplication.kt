package com.shoppinglist.application

import com.shoppinglist.common.makeLogger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
private val logger = makeLogger {}

@SpringBootApplication(scanBasePackages = ["com.shoppinglist.application", "com.shoppinglist.configuration", "com.shoppinglist.common"])
class ShoppingListBackendKotApplication

fun main(args: Array<String>) {
    logger.info("STARTING APPLICATION....")
    runApplication<ShoppingListBackendKotApplication>(*args)
    logger.info("RUNNING APPLICATION....")
}
