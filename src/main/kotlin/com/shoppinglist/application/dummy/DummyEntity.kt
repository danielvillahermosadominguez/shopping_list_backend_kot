package com.shoppinglist.application.dummy

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id

@Entity
class DummyEntity(
    @jakarta.persistence.Id @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null
) {
}

