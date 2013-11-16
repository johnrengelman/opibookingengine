package net.opihackday.agileniagara.app

import com.mongodb.Mongo
import net.opihackday.agileniagara.amqp.AmqpHandler
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories
class Booker {

    @Bean
    Mongo mongo() {
        return new Mongo('localhost')
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

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext('net.opihackday.agileniagara')
    }
}
