package net.opihackday.agileniagara.domain

import groovy.transform.TupleConstructor
import org.springframework.data.annotation.Id

@TupleConstructor(excludes=['id'])
class Location {

    @Id
    String id

    String name
}
