package net.opihackday.agileniagara.config

import com.mongodb.Mongo
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
@EnableMongoRepositories(basePackages = ['net.opihackday.agileniagara.repositories'])
class BookerConfig {

    @Bean
    Mongo mongo(@Value('${mongo.host:localhost}') String host, @Value('${mongo.port:27017}') String port) {
        new Mongo(host, port.toInteger())
    }

    @Bean
    MongoTemplate mongoTemplate(Mongo mongo) {
        new MongoTemplate(mongo, 'opi-niagara-booking')
    }

    @Bean
    ConnectionFactory connectionFactory() {
//        ConnectionFactory cf = new CachingConnectionFactory('lemur.cloudamqp.com', 5672)
//        cf.username = 'jzqonxoa'
//        cf.password = 'am3WlvBWtGJ95WIN4Uuxuu3uWdXYcYHJ'
//        cf.virtualHost = 'jzqonxoa'
        ConnectionFactory cf = new CachingConnectionFactory('192.168.168.227', 5672)
        cf.username = 'guest'
        cf.password = 'guest'
        cf.virtualHost = 'niagara'
        cf
    }

    @Bean
    AmqpAdmin amqpAdmin(ConnectionFactory cf) {
        new RabbitAdmin(cf)
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    AbstractMessageListenerContainer messageContainer(ConnectionFactory cf) {
        new SimpleMessageListenerContainer(cf)
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        new RabbitTemplate(cf)
    }

    @Bean
    JavaMailSender mailSender() {
      JavaMailSender sender = new JavaMailSenderImpl()
      sender.host = "smtp.gmail.com"
      sender.port = 587
      sender.username = System.properties['email.user']
      sender.password = System.properties['email.pass']

      def props = [:]
      props['mail.smtp.auth'] = "true"
      props['mail.smtp.starttls.enable'] = "true"

      sender.javaMailProperties = props as Properties
      sender
    }
}
