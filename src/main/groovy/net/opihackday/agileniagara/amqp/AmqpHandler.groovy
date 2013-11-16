package net.opihackday.agileniagara.amqp

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.amqp.core.Queue

class AmqpHandler implements MessageListener {

    AbstractMessageListenerContainer container

    @Autowired
    AmqpAdmin amqpAdmin

    @Autowired
    ConnectionFactory connectionFactory

    void initListeners(List<Queue> queues, ConnectionFactory cf) {
        container = new SimpleMessageListenerContainer(cf)
        container.setMessageListener( new MessageListenerAdapter(this))
        container.setQueues(queues as Queue[])
        container.start()
    }

    void start() {
        Queue queue = new Queue('bookingRequest')
        TopicExchange exchange = new TopicExchange('bookingExchange')
        amqpAdmin.declareExchange(exchange)
        amqpAdmin.declareBinding(
                BindingBuilder.bind(queue).to(exchange).with('request.*')
        )

        initListeners([queue], cf)
    }

    void stop() {
        container.stop()
    }

    @Override
    void onMessage(Message message) {

    }
}
