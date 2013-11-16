package net.opihackday.agileniagara.repositories

import net.opihackday.agileniagara.domain.Season
import org.springframework.data.mongodb.repository.MongoRepository

interface SeasonRepository extends MongoRepository<Season, String> {
}
