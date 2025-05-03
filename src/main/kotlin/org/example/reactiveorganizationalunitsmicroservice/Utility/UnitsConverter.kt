package org.example.reactiveorganizationalunitsmicroservice.Utility

import org.example.reactiveorganizationalunitsmicroservice.Boundary.UnitBoundary
import org.example.reactiveorganizationalunitsmicroservice.Entity.UnitEntity
import org.springframework.stereotype.Component

@Component
class UnitsConverter {

    fun toBoundary(entity: UnitEntity): UnitBoundary {
        return UnitBoundary(
            entity.id,
            entity.name,
            entity.creationDate,
            entity.description
        )
    }

    fun toEntity(boundary: UnitBoundary): UnitEntity {
        return UnitEntity(
            boundary.id,
            boundary.name,
            boundary.creationDate,
            boundary.description,
            hashSetOf()
        )
    }
}