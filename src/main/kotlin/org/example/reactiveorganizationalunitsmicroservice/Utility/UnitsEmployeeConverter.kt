package org.example.reactiveorganizationalunitsmicroservice.Utility

import org.example.reactiveorganizationalunitsmicroservice.Boundary.UnitEmployeeBoundary
import org.example.reactiveorganizationalunitsmicroservice.Entity.UnitEmployeeEntity
import org.springframework.stereotype.Component

@Component
class UnitsEmployeeConverter {

    fun toBoundary(entity: UnitEmployeeEntity): UnitEmployeeBoundary {
        return UnitEmployeeBoundary(
            entity.email
        )
    }
}