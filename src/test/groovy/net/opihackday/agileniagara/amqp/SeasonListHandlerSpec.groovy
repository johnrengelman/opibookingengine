package net.opihackday.agileniagara.amqp

import net.opihackday.agileniagara.domain.Season
import net.opihackday.agileniagara.repositories.SeasonRepository
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(locations = ['classpath:applicationContext.xml'])
class SeasonListHandlerSpec {

    @Autowired
    SeasonRepository seasonRepository

    @Autowired
    SeasonListHandler seasonListHandler

    def 'return seasons'() {
        given:
        Season season1 = new Season(
                startDate: new LocalDate(2013, 5, 1),
                endDate: new LocalDate(2013, 10, 1)
        )
        Season season2 = new Season(
                startDate: new LocalDate(2013, 10, 1),
                endDate: new LocalDate(2014, 5, 1)
        )
        seasonRepository.save([season1, season2])

        when:
        List<Map> results = seasonListHandler.handleMessage([])

        then:
        assert results.size() == 2
    }
}
