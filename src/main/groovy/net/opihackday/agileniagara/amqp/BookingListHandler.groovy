package net.opihackday.agileniagara.amqp

import groovy.util.logging.Slf4j
import net.opihackday.agileniagara.domain.Booking
import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.domain.Season
import net.opihackday.agileniagara.repositories.BookingRepository
import net.opihackday.agileniagara.repositories.LocationRepository
import net.opihackday.agileniagara.repositories.SeasonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Slf4j
class BookingListHandler extends DefaultHandler<List<Map>> {

    @Autowired
    LocationRepository locationRepository

    @Autowired
    SeasonRepository seasonRepository

    @Autowired
    BookingRepository bookingRepository

    @Override
    List<Map> handleMessage(Map data) {
        Location location
        Season season
        log.info("received: $data")
        if (!data.locationId) {
            throw new IllegalArgumentException('Must provide a valid Location ID')
        } else {
            location = locationRepository.findOne(data.locationId)
            if (!location) {
                throw new IllegalArgumentException("No Location found for ID: ${data.locationId}")
            }
        }
        if (!data.seasonId) {
            throw new IllegalArgumentException('Must provide a valid season ID')
        } else {
            season = seasonRepository.findOne(data.seasonId)
            if (!season) {
                throw new IllegalArgumentException("No Season found for ID: ${data.seasonId}")
            }
        }
        log.info("Finding bookings for Location [${location.name} between [${season.startDate}] - [${season.endDate}]")
        List<Booking> bookings = bookingRepository.findByLocation(location).findAll {
            it.startDate >= season.startDate && it.endDate <= season.endDate
        }
        bookings.collect {
            it.toMap()
        }

    }

    @Override
    String getQueueName() {
        'booking.list'
    }
}
