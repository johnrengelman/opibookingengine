package net.opihackday.agileniagara.amqp

import groovy.util.logging.Slf4j
import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.domain.Season
import net.opihackday.agileniagara.repositories.LocationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Slf4j
class LocationListHandler extends DefaultHandler<List<Map>> {

    @Autowired
    LocationRepository locationRepository

    @Override
    List<Map> handleMessage(Map data) {
        log.info("received: $data")
        List<Location> locations = locationRepository.findAll()
        locations.collect {
            it.toMap()
        }
    }

    @Override
    String getQueueName() {
        'location.list'
    }
}
