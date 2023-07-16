package com.shoppinglist.common

import com.configcat.ConfigCatClient
import com.configcat.ConfigCatUser
import com.configcat.getValue
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FeatureToggle(
    @Autowired private val configCatClient: ConfigCatClient,
) {
    fun isToggleEnabled(toggleName: FeatureToggleNames): Boolean {
        return runBlocking {
            configCatClient.getValue(toggleName.toggleKey, false)
        }
    }

    fun isToggleEnabledForUser(toggleName: FeatureToggleNames, email: String): Boolean {
        return runBlocking {
            configCatClient.getValue(
                toggleName.toggleKey,
                defaultValue = false,
                user = ConfigCatUser(email),
            )
        }
    }

    companion object {
        fun close() {
            ConfigCatClient.closeAll()
        }
    }
}
