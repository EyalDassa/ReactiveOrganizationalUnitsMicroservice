package org.example.reactiveorganizationalunitsmicroservice.Controller

import org.example.reactiveorganizationalunitsmicroservice.Boundary.UnitBoundary
import org.example.reactiveorganizationalunitsmicroservice.Boundary.UnitEmployeeBoundary
import org.example.reactiveorganizationalunitsmicroservice.Service.UnitsService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(path = ["/units"])
class UnitsController (
    val unitsService: UnitsService
) {

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun create(
    @RequestBody unit: UnitBoundary): Mono<UnitBoundary> {
        return this.unitsService.create(unit)
    }

    @GetMapping(
        path = ["/{unitId}"],
        produces = [MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable unitId: String): Mono<UnitBoundary> {
        return this.unitsService.findById(unitId)
    }

    @GetMapping(
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun findAllUnits(
        @RequestParam(name = "page", required = false ,defaultValue = "0") page: Int,
        @RequestParam(name = "size", required = false ,defaultValue = "10") size: Int): Flux<UnitBoundary> {
        return this.unitsService.findAllById(page,size)
    }

    @PutMapping(
        path = ["/{unitId}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun update(@PathVariable unitId: String,
                     @RequestBody unit: UnitBoundary): Mono<Void> {
        return this.unitsService.updateUnit(unitId, unit)
    }

    @DeleteMapping
    fun clean(): Mono<Void> {
        return this.unitsService.clean()
    }

    @PutMapping(
        path = ["/{unitId}/users"],
        consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun bindEmployee(@PathVariable unitId: String,
                            @RequestBody employeeBoundary: UnitEmployeeBoundary): Mono<Void> {
        return this.unitsService.bindEmployee(unitId, employeeBoundary)
    }

    @GetMapping(
        path = ["/{unitId}/users"],
        produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun findEmployeesOfUnit(@PathVariable unitId: String,
                            @RequestParam(name = "page", required = false ,defaultValue = "0") page: Int,
                            @RequestParam(name = "size", required = false ,defaultValue = "10") size: Int): Flux<UnitEmployeeBoundary> {
        return this.unitsService.findEmployeesOfUnit(unitId, page, size)
    }

    @GetMapping(
            path = ["/{email}/units"],
            produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun findUnitsOfEmployee(@PathVariable email: String,
                            @RequestParam(name = "page", required = false ,defaultValue = "0") page: Int,
                            @RequestParam(name = "size", required = false ,defaultValue = "10") size: Int): Flux<UnitBoundary> {
        return this.unitsService.findUnitsOfEmployee(email, page, size)
    }

    @DeleteMapping(
        path = ["/{unitId}/users"])
    fun unbindEmployee(@PathVariable unitId: String):Mono<Void>{
        return this.unitsService.unBindEmployeesFromUnit(unitId)
    }

}