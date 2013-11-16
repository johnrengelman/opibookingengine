package net.opihackday.agileniagara.amqp

import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.domain.Season
import net.opihackday.agileniagara.repositories.LocationRepository
import net.opihackday.agileniagara.repositories.SeasonRepository
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(locations = ['classpath:applicationContext.xml'])
class LocationListHandlerSpec {

    @Autowired
    LocationRepository locationRepository

    @Autowired
    LocationListHandler locationListHandler

    def 'return seasons'() {
        given:
        Location location1 = new Location(
                name: 'condo'
        )
        Location location2 = new Location(
                name: 'cabin'
        )
        locationRepository.save([location1, location2])

        when:
        List<Map> results = locationListHandler.handleMessage([])

        then:
        assert results.size() == 2
        assert results.find { it.name == 'condo' }
        assert results.find { it.name == 'cabin' }
    }
}
