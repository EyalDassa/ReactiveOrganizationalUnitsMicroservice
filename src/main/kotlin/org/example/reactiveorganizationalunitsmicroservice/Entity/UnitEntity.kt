package org.example.reactiveorganizationalunitsmicroservice.Entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "units")
class UnitEntity (
    @Id var id: String?,
    var name: String?,
    var creationDate: String?,
    var description: String?){

    constructor(): this(null, null, null, null)

    override fun toString(): String {
        return "UnitsEntity(id=$id, name=$name, creationDate=$creationDate, description=$description)"
    }

}