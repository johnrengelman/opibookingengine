package net.opihackday.agileniagara.amqp

import groovy.util.logging.Slf4j
import net.opihackday.agileniagara.api.BookingRequest
import net.opihackday.agileniagara.domain.Booking
import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.repositories.BookingRepository
import net.opihackday.agileniagara.repositories.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Slf4j
class BookingRequestHandler extends DefaultHandler {

    @Autowired
    LocationRepository locationRepository

    @Autowired
    BookingRepository bookingRepository

    @Override
    Map handleMessage(Map data) {
        println "received a map: ${data}"
        BookingRequest request = new BookingRequest(data)
        Location location = locationRepository.findOne(request.locationId)
        if (!location) {
            log.error("Error processing booking for [${request.username}]. " +
                    "Location [${request.locationId}] does not exist")
        }
        def booking = bookingRepository.save(new Booking(
                username: request.username,
                location: location,
                startDate: request.startDate,
                endDate: request.endDate
        ))
        booking.toMap()
    }

    String getQueueName() {
        'booking.request'
    }
}
