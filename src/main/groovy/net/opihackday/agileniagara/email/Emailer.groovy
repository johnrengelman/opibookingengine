package net.opihackday.agileniagara.email

import groovy.util.logging.Slf4j
import net.opihackday.agileniagara.domain.Booking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
@Slf4j
class Emailer {

  @Autowired
  JavaMailSender mailSender

  void sendSuccessfulBooking(Booking booking) {
    SimpleMailMessage simpleMessage = new SimpleMailMessage()
    simpleMessage.with {
      setTo([booking.username] as String[])
      from = "blah@gmail.com" // doesn't matter...
      subject = "We've reserved your spot at the $booking.location.name"
      text = """\
        | Dear $booking.username,
        |
        | We've reserved your spot at the $booking.location.name for the following dates:
        | $booking.startDate through $booking.endDate
        |
        | Please visit the OPI Socialcast page for directions and details.
        |
        | Thanks!
        | OPI Booking Genie
      """.stripMargin()
      it
    }
    log.info("Sending booking email to ${booking.username} for [${booking.id}")
    mailSender.send simpleMessage
  }

}
