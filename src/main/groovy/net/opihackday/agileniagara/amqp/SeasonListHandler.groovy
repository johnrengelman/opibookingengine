package net.opihackday.agileniagara.amqp

import groovy.util.logging.Slf4j
import net.opihackday.agileniagara.domain.Season
import net.opihackday.agileniagara.repositories.SeasonRepository
import org.springframework.beans.factory.annotation.Autowired

@Slf4j
class SeasonListHandler extends DefaultHandler<List<Map>> {

    @Autowired
    SeasonRepository seasonRepository

    @Override
    List<Map> handleMessage(Map data) {
        log.info("received: $data")
        List<Season> seasons = seasonRepository.findAll()
        seasons.collect {
            it.toMap()
        }
    }

    @Override
    String getQueueName() {
        'season.list'
    }
}
