package net.opihackday.agileniagara.api

import org.joda.time.LocalDate

class BookingRequest implements Serializable {

    String username
    String locationName
    LocalDate startDate
    LocalDate endDate
}
