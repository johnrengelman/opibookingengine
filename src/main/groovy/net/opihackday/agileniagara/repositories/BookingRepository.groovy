package net.opihackday.agileniagara.repositories

import net.opihackday.agileniagara.domain.Booking
import org.springframework.data.mongodb.repository.MongoRepository

interface BookingRepository extends MongoRepository<Booking, String> {

    List<Booking> findByUsername(String username)
}
