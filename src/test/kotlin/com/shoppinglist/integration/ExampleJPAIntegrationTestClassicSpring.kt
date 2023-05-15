package com.shoppinglist.integration

import com.shoppinglist.application.ShoppingListBackendKotApplication
import com.shoppinglist.application.dummy.DummyEntity
import com.shoppinglist.application.dummy.DummyRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [(ShoppingListBackendKotApplication::class)])
@ComponentScan("com.shoppinglist.application.dummy")
@EnableAutoConfiguration
class ExampleJPAIntegrationTestClassicSpring {
    @Autowired
    lateinit var entityManager: TestEntityManager

    @Autowired
    lateinit var repository: DummyRepository

    @Test
    fun dummyExampleOfIntegrationTestWithDB() {
        val dummyEntity = DummyEntity()
        entityManager.persist(dummyEntity)
        entityManager.flush()
        val theSameDummyEntity = this.repository.findAll().toList()
        assertThat(theSameDummyEntity.size).isEqualTo(1)
        assertThat(theSameDummyEntity[0]).isEqualTo(dummyEntity)
    }
}
