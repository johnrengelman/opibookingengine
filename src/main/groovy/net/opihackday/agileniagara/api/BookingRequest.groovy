package net.opihackday.agileniagara.api

import org.joda.time.LocalDate

class BookingRequest implements Serializable {

    BookingRequest(Map map) {
        this.username = map.username
        this.locationId = map.locationId
        this.startDate = new LocalDate(map.startDate)
        this.endDate = new LocalDate(map.endDate)
    }

    String username
    String locationId
    LocalDate startDate
    LocalDate endDate
}
