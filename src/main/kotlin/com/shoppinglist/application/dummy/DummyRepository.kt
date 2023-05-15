package com.shoppinglist.application.dummy

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DummyRepository : CrudRepository<DummyEntity, Long>
