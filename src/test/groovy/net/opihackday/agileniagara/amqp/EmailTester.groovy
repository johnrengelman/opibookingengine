package net.opihackday.agileniagara.amqp

import net.opihackday.agileniagara.domain.Booking
import net.opihackday.agileniagara.domain.Location
import net.opihackday.agileniagara.email.Emailer
import org.joda.time.LocalDate
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * User: danielwoods
 * Date: 11/16/13
 */
class EmailTester {


  static void main(_) {
    def ctx = new ClassPathXmlApplicationContext('classpath:applicationContext.xml')
    Emailer emailer = (Emailer)ctx.getBean("emailer")
    def booking = new Booking().with {
      location = new Location().with {
        it.name = "Cabin"
        it
      }
      it.startDate = new LocalDate("2013-12-01")
      it.endDate = new LocalDate("2013-12-13")
      it.username = "daniel.woods@objectpartners.com"
      it
    }
    emailer.sendSuccessfulBooking(booking)
  }
}
