package com.shoppinglist.acceptance

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["classpath:cucumber/features/dummy.feature"],
    glue = ["classpath:com/shoppinglist/acceptance"],
    plugin = ["pretty", "json:target/jsonReports/acceptance.json", "html:target/cucumber/html", "html:target/cucumber/acceptance.html"],
)
class CucumberAppTestRunner
