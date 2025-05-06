package org.example.reactiveorganizationalunitsmicroservice.Service

import org.apache.commons.validator.routines.EmailValidator
import org.example.reactiveorganizationalunitsmicroservice.Boundary.UnitBoundary
import org.example.reactiveorganizationalunitsmicroservice.Boundary.UnitEmployeeBoundary
import org.example.reactiveorganizationalunitsmicroservice.Entity.UnitEmployeeCrud
import org.example.reactiveorganizationalunitsmicroservice.Entity.UnitEmployeeEntity
import org.example.reactiveorganizationalunitsmicroservice.Utility.UnitsConverter
import org.example.reactiveorganizationalunitsmicroservice.Entity.UnitsCrud
import org.example.reactiveorganizationalunitsmicroservice.Exceptions.InvalidInputException
import org.example.reactiveorganizationalunitsmicroservice.Exceptions.UnitNotFoundException
import org.example.reactiveorganizationalunitsmicroservice.Utility.UnitsEmployeeConverter
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.text.SimpleDateFormat
import java.util.Date

@Service
class UnitsServiceImpl(
    val unitsCrud: UnitsCrud,
    val unitsEmployeeCrud: UnitEmployeeCrud,
    val converter: UnitsConverter,
    val unitsEmployeeConverter: UnitsEmployeeConverter,
) : UnitsService{

    override fun create(unit: UnitBoundary): Mono<UnitBoundary> {
        unit.id = null
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        unit.creationDate = formatter.format(Date())

        return Mono.just(unit)
            .flatMap {
                if (it.name == null || it.name!!.trim().isEmpty()){
                    Mono.error{InvalidInputException("Name cannot be null or empty")}
                }
                else if (it.description == null || it.description!!.trim().isEmpty()) {
                    Mono.error {InvalidInputException("Description cannot be null or empty")}
                }
                else Mono.just(it)
            }
            .map { converter.toEntity(it) }
            .flatMap{unitsCrud.save(it)}
            .log()
            .map { converter.toBoundary(it) }
            .log()
    }

    override fun findById(unitId: String): Mono<UnitBoundary> {
        return this.unitsCrud
            .findById(unitId)
            .log()
            .map { converter.toBoundary(it) }
            .log()
    }

    override fun clean(): Mono<Void> {
        return this.unitsCrud.deleteAll()
            .log()
            .then(this.unitsEmployeeCrud.deleteAll()
                .log())
    }

    override fun findAllById(page: Int, size: Int): Flux<UnitBoundary> {
        return this.unitsCrud.findAllByIdNotNull(PageRequest.of(
            page, size, Sort.Direction.ASC,"creationDate","id"))
            .map { converter.toBoundary(it) }
            .log()
    }

    override fun updateUnit(unitId: String, unit: UnitBoundary): Mono<Void> {
        return this.unitsCrud
            .findById(unitId)
            .switchIfEmpty(Mono.error{ UnitNotFoundException("Unit with ID $unitId was not found")})
            .log()
            .map{
                if(unit.name != null) it.name = unit.name
                if(unit.description != null) it.description = unit.description
                it
           }
            .flatMap { unitsCrud.save(it) }
            .log()
            .then()
    }

    override fun bindEmployee(unitId: String, boundary: UnitEmployeeBoundary): Mono<Void> {
        return this.unitsCrud
            .findById(unitId)
            .switchIfEmpty(Mono.empty())
            .log()
            .zipWith(Mono.just(boundary)
                .flatMap {
                    if (it.email == null || it.email!!.trim().isEmpty() || !EmailValidator.getInstance().isValid(it.email)) {
                        Mono.empty()
                    } else {
                        Mono.just(it)
                    }
                }
                .log()
                .map {
                    val email = it.email!!.trim()
                    val id = "$email$$unitId"
                    UnitEmployeeEntity(
                        id,
                        email,
                        unitId
                    )
                })
            .flatMap { unitsEmployeeCrud.save(it.t2) }
            .log()
            .then()
    }

    override fun findEmployeesOfUnit(unitId: String, page: Int, size: Int): Flux<UnitEmployeeBoundary> {
        return this.unitsEmployeeCrud
            .findAllByUnitId(unitId, PageRequest.of(
                page, size, Sort.Direction.ASC,"email"))
            .log()
            .map { unitsEmployeeConverter.toBoundary(it) }
    }

    override fun findUnitsOfEmployee(email: String, page: Int, size: Int): Flux<UnitBoundary> {
        return this.unitsEmployeeCrud
            .findAllByEmail(email, PageRequest.of(page, size, Sort.by("unitId")))
            .concatMap {  // preserves ordering
                this.unitsCrud.findById(it.unitId!!)
                    .map(converter::toBoundary)
            }
    }

    override fun unBindEmployeesFromUnit(unitId: String): Mono<Void> {
        return this.unitsEmployeeCrud
            .deleteAllByUnitId(unitId)
            .log()
            .then()
    }

}
