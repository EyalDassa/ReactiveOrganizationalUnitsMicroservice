package org.example.reactiveorganizationalunitsmicroservice.Entity

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface UnitEmployeeCrud: ReactiveMongoRepository<UnitEmployeeEntity, String> {
    fun findAllByUnitId(unitId: String, pageable: Pageable): Flux<UnitEmployeeEntity>
    fun deleteAllByUnitId(unitId: String): Flux<UnitEmployeeEntity>
    fun findAllByEmail(email: String, pageable: Pageable): Flux<UnitEmployeeEntity>
}