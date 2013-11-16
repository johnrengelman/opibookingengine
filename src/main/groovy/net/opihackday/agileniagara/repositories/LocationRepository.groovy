package net.opihackday.agileniagara.repositories

import net.opihackday.agileniagara.domain.Location
import org.springframework.data.mongodb.repository.MongoRepository

interface LocationRepository extends MongoRepository<Location, String> {

    Location findByName(String name)
}
