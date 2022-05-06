package city.samaritan.pokemongo.model

import java.time.LocalDateTime

data class Token(val token: String, val expiresAt: LocalDateTime)