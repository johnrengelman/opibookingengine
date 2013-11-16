package net.opihackday.agileniagara.amqp

import groovy.util.logging.Slf4j
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Slf4j
@Component
class AmqpHandler implements InitializingBean {

    @Autowired
    AmqpAdmin amqpAdmin

    TopicExchange exchange

    @Override
    void afterPropertiesSet() throws Exception {
        exchange = new TopicExchange('booking')
        amqpAdmin.declareExchange(exchange)
    }
}
