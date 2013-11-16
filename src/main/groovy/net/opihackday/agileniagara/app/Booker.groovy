package net.opihackday.agileniagara.app

import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.domain.Season
import net.opihackday.agileniagara.repositories.LocationRepository
import net.opihackday.agileniagara.repositories.SeasonRepository
import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.stereotype.Component

@Component
class Booker {

    @Autowired
    LocationRepository locationRepository

    @Autowired
    SeasonRepository seasonRepository

    public static void main(String[] args) {
        def ctx = new ClassPathXmlApplicationContext('applicationContext.xml')
        Booker booker = ctx.getBean(Booker)
        booker.init()
    }

    public void init() {
        ['condo', 'cabin'].each { name ->
            if (!locationRepository.findByName(name)) {
                locationRepository.save(new Location(name: name))
            }
        }
        [
                [startDate: '2013-11-1', endDate: '2014-05-01', name: 'Winter 2013'],
                [startDate: '2014-05-01', endDate: '2014-11-1', name: 'Summer 2014']
        ].each { sInfo ->
            if (!seasonRepository.findByStartDate(new LocalDate(sInfo.startDate))) {
                seasonRepository.save(new Season(
                        startDate: new LocalDate(sInfo.startDate),
                        endDate: new LocalDate(sInfo.endDate),
                        name: name
                ))
            }
        }
    }
}
