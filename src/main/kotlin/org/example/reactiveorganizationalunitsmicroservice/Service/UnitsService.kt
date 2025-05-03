package org.example.reactiveorganizationalunitsmicroservice.Service

import org.example.reactiveorganizationalunitsmicroservice.Boundary.UnitBoundary
import org.example.reactiveorganizationalunitsmicroservice.Boundary.UnitEmployeeBoundary
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UnitsService {
    fun create(unit: UnitBoundary): Mono<UnitBoundary>
    fun findById(unitId: String): Mono<UnitBoundary>
    fun clean(): Mono<Void>
    fun findAllById(page: Int, size: Int): Flux<UnitBoundary>
    fun updateUnit(unitId: String, unit: UnitBoundary): Mono<Void>
    fun bindEmployee(unitId: String, employeeBoundary: UnitEmployeeBoundary): Mono<Void>
    fun findEmployeesOfUnit(unitId: String, page: Int, size: Int): Flux<UnitBoundary>
}
