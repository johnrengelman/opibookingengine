package net.opihackday.agileniagara.amqp

import net.opihackday.agileniagara.api.BookingRequest
import net.opihackday.agileniagara.domain.Booking
import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.repositories.BookingRepository
import net.opihackday.agileniagara.repositories.LocationRepository
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(locations = ['classpath:applicationContext.xml'])
class AmqpHandlerSpec extends Specification {

    @Autowired
    AmqpHandler handler

    @Autowired
    LocationRepository locationRepository

    @Autowired
    BookingRepository bookingRepository

    def 'persist a valid booking request'() {
        given:
        Location location = new Location(name: 'condo')
        locationRepository.save(location)
        BookingRequest request = new BookingRequest(
                username: 'john',
                locationName: location.name,
                startDate: new LocalDate(2013, 11, 16),
                endDate: new LocalDate(2013, 11, 17)
        )

        when:
        handler.handleMessage(request)

        then:
        List<Booking> savedBookings = bookingRepository.findByUsername(request.username)
        assert savedBookings.size() == 1
    }
}
