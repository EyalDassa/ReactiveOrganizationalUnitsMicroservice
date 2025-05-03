package org.example.reactiveorganizationalunitsmicroservice.Boundary

class UnitEmployeeBoundary(
    var email : String?) {

    constructor(): this(null)

    override fun toString(): String {
        return "UnitEmployeeBoundary(email=$email)"
    }
}