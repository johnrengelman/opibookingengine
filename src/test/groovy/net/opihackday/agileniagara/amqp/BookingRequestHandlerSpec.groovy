package net.opihackday.agileniagara.amqp

import net.opihackday.agileniagara.domain.Booking
import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.repositories.BookingRepository
import net.opihackday.agileniagara.repositories.LocationRepository
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(locations = ['classpath:applicationContext.xml'])
class BookingRequestHandlerSpec extends Specification {

    @Autowired
    BookingRequestHandler handler

    @Autowired
    LocationRepository locationRepository

    @Autowired
    BookingRepository bookingRepository

    def 'persist a valid booking request'() {
        given:
        Location location = new Location(name: 'condo')
        locationRepository.save(location)

        when:
        Map returnVal = handler.handleMessage([
                username: 'john',
                locationId: location.id,
                startDate: new LocalDate(2013, 11, 16).toString(),
                endDate: new LocalDate(2013, 11, 17).toString()
        ])

        then:
        List<Booking> savedBookings = bookingRepository.findByUsername('john')
        assert savedBookings.size() == 1
        assert returnVal.id == savedBookings[0].id
    }
}
