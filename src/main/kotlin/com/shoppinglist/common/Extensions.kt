package com.shoppinglist.common

import mu.KLogger
import mu.KotlinLogging

fun makeLogger(func: () -> Unit): KLogger {
    func() // for test coverage
    return KotlinLogging.logger(func)
}
