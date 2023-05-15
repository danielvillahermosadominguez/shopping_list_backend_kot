package com.shoppinglist.application.dummy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DummyService(
    @Autowired val repository: DummyRepository,
) {
    fun getAll() = this.repository.findAll()
}
