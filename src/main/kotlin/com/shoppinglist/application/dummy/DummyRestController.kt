package com.shoppinglist.application.dummy

import com.shoppinglist.common.FeatureToggle
import com.shoppinglist.common.FeatureToggleNames
import com.shoppinglist.common.makeLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
private val logger = makeLogger {}

@RestController
@RequestMapping("api/dummy")
class DummyRestController(
    @Autowired var dummyService: DummyService,
    @Autowired var featureToggle: FeatureToggle,
) {
    @GetMapping
    fun dummyGet(): ResponseEntity<List<DummyEntity>> {
        if (featureToggle.isToggleEnabled(FeatureToggleNames.IS_AN_EXAMPLE_ENABLED)) {
            logger.info { "Feature toggle example is enabled" }
        } else {
            logger.info { "Feature toggle example is disabled" }
        }

        if (featureToggle.isToggleEnabledForUser(FeatureToggleNames.IS_AN_EXAMPLE_WITH_USER_ENABLED, "example.mail@mail.com")) {
            logger.info { "Feature toggle example with mail is enabled for example.mail@mail.com" }
        } else {
            logger.info { "Feature toggle example with mail is disabled for example.mail@mail.com" }
        }
        return ResponseEntity(dummyService.getAll().toList(), HttpStatus.OK)
    }
}
