package net.opihackday.agileniagara.config

import com.mongodb.Mongo
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ['net.opihackday.agileniagara.repositories'])
class BookerConfig {

    @Bean
    Mongo mongo(@Value('${mongo.host:localhost}') String host, @Value('${mongo.port:27017}') String port) {
        return new Mongo(host, port.toInteger())
    }

    @Bean
    MongoTemplate mongoTemplate(Mongo mongo) {
        return new MongoTemplate(mongo, 'opi-niagara-booking')
    }

    @Bean
    ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new CachingConnectionFactory('lemur.cloudamqp.com', 5672)
        cf.username = 'jzqonxoa'
        cf.password = 'am3WlvBWtGJ95WIN4Uuxuu3uWdXYcYHJ'
        cf.virtualHost = 'jzqonxoa'
        return cf
    }

    @Bean
    AmqpAdmin amqpAdmin(ConnectionFactory cf) {
        return new RabbitAdmin(cf)
    }
}
