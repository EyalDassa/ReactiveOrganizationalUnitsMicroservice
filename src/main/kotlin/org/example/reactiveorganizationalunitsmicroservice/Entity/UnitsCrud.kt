package org.example.reactiveorganizationalunitsmicroservice.Entity

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux


interface UnitsCrud: ReactiveMongoRepository<UnitEntity, String> {
    fun findAllByIdNotNull(pageable: Pageable): Flux<UnitEntity>
}