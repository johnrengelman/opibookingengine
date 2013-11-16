package net.opihackday.agileniagara.domain

import groovy.transform.TupleConstructor
import org.joda.time.LocalDate
import org.springframework.data.annotation.Id

@TupleConstructor(excludes=['id'])
class Season {

    @Id
    String id

    LocalDate startDate
    LocalDate endDate
    String name

    Map toMap() {
        [
                id: id,
                startDate: startDate.toString(),
                endDate: endDate.toString(),
                name: name
        ]
    }
}
