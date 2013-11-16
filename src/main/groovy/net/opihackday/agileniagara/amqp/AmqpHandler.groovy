package net.opihackday.agileniagara.amqp

import groovy.util.logging.Slf4j
import net.opihackday.agileniagara.api.BookingRequest
import net.opihackday.agileniagara.domain.Booking
import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.repositories.BookingRepository
import net.opihackday.agileniagara.repositories.LocationRepository
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.amqp.core.Queue
import org.springframework.stereotype.Component

@Slf4j
@Component
class AmqpHandler implements InitializingBean, DisposableBean {

    AbstractMessageListenerContainer container
    RabbitTemplate template

    @Autowired
    AmqpAdmin amqpAdmin

    @Autowired
    ConnectionFactory connectionFactory

    @Autowired
    BookingRepository bookingRepository

    @Autowired
    LocationRepository locationRepository

    void initListeners(List<Queue> queues) {
        container = new SimpleMessageListenerContainer(connectionFactory)
        template = new RabbitTemplate(connectionFactory)
        container.setMessageListener( new MessageListenerAdapter(this))
        container.setQueues(queues as Queue[])
        container.start()
    }

    void start() {
        Queue queue = new Queue('bookingRequest')
        TopicExchange exchange = new TopicExchange('bookingExchange')
        amqpAdmin.declareExchange(exchange)
        amqpAdmin.declareQueue(queue)
        amqpAdmin.declareBinding(
                BindingBuilder.bind(queue).to(exchange).with('request.*')
        )

        initListeners([queue])
    }

    void stop() {
        container.stop()
    }

    @Override
    void handleMessage(BookingRequest request) {
        Location location = locationRepository.findByName(request.locationName)
        if (!location) {
            log.error("Error processing booking for [${request.username}]. " +
                    "Location [${request.locationName}] does not exist")
        }
        bookingRepository.save(new Booking(
                username: request.username,
                location: location,
                startDate: request.startDate,
                endDate: request.endDate
        ))
    }

    @Override
    void destroy() throws Exception {
        stop()
    }

    @Override
    void afterPropertiesSet() throws Exception {
        start()
    }
}
