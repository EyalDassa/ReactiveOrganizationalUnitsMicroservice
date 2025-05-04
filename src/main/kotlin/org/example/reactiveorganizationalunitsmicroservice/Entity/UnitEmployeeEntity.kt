package org.example.reactiveorganizationalunitsmicroservice.Entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "employees")
class UnitEmployeeEntity (
    @Id var id: String?,
    var email: String?,
    var unitId: String? ){

    constructor(): this(null, null, null)

    override fun toString(): String {
        return "UnitEmployeeEntity(id=$id, email=$email, unitId=$unitId)"
    }
}