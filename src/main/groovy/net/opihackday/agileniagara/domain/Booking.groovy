package net.opihackday.agileniagara.domain

import groovy.transform.TupleConstructor
import org.joda.time.LocalDate
import org.springframework.data.annotation.Id

@TupleConstructor(excludes=['id'])
class Booking {

    @Id
    private String id

    String username
    Location location
    LocalDate startDate
    LocalDate endDate
}
