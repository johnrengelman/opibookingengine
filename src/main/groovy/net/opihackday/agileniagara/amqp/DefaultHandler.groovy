package net.opihackday.agileniagara.amqp

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.amqp.core.Queue

abstract class DefaultHandler implements InitializingBean, DisposableBean {

    @Autowired
    AmqpHandler handler

    @Autowired
    AbstractMessageListenerContainer container

    @Autowired
    AmqpAdmin amqpAdmin

    @Override
    void afterPropertiesSet() throws Exception {
        container.setMessageListener(new MessageListenerAdapter(this))
        Queue q = new Queue(queueName)
        amqpAdmin.declareQueue(q)
        container.setQueues([q] as Queue[])
        amqpAdmin.declareBinding(
                BindingBuilder.bind(q).to(handler.exchange).with(queueName)
        )
        container.start()
    }

    @Override
    void destroy() throws Exception {
        container.destroy()
    }

    abstract Map handleMessage(Map data)
    abstract String getQueueName()
}
