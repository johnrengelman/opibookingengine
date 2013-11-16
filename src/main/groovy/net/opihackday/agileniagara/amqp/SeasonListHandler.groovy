package net.opihackday.agileniagara.amqp

import groovy.util.logging.Slf4j
import net.opihackday.agileniagara.domain.Season
import net.opihackday.agileniagara.repositories.SeasonRepository
import org.joda.time.LocalDate
import org.joda.time.Weeks
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Slf4j
class SeasonListHandler extends DefaultHandler<List<Map>> {

    @Autowired
    SeasonRepository seasonRepository

    @Autowired
    RabbitTemplate rabbitTemplate

    @Override
    List<Map> handleMessage(Map data) {
        log.info("received: $data")
        if (!data.username) {
            throw new IllegalArgumentException('Must provider a username!')
        }
        Map user = rabbitTemplate.convertSendAndReceive('users', 'user.list', [email: data.username])
        log.info("retrieved: $user")
        if (!user) {
            throw new IllegalArgumentException("No user found for email: ${data.username}")
        }
        LocalDate today = new LocalDate()
        def roles = [
                [role: 'SPURGAT', window: Weeks.weeks(4)],
                [role: 'SENIOR', window: Weeks.weeks(2)],
                [role: 'USER', windows: Weeks.weeks(1)]
        ]
        def role = roles.find { it.role == user.role}
        List<Season> seasons = seasonRepository.findAll()
        List<Season> filteredSeasons = seasons.findAll {
            (today >= it.startDate.minus(role.window) && today < it.endDate)
        }
        log.info("All seasons: $seasons")
        log.info("Filtered seasons: $filteredSeasons")
        filteredSeasons.collect {
            it.toMap()
        }
    }

    @Override
    String getQueueName() {
        'season.list'
    }
}
