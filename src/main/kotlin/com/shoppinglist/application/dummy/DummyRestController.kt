package com.shoppinglist.application.dummy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/dummy")
class DummyRestController (@Autowired var dummyService: DummyService){
    @GetMapping
    fun dummyGet(): ResponseEntity<List<DummyEntity>> {
        return ResponseEntity(dummyService.getAll().toList(), HttpStatus.OK)
    }
}