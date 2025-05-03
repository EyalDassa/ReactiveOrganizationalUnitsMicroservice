package org.example.reactiveorganizationalunitsmicroservice.Boundary

class UnitBoundary(
    var id: String?,
    var name: String?,
    var creationDate: String?,
    var description: String?) {

    constructor(): this(null, null, null, null)

    override fun toString(): String {
        return "UnitBoundary(id=$id, name=$name, creationDate=$creationDate, description=$description)"
    }
}