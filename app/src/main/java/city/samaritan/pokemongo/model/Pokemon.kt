package city.samaritan.pokemongo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.samaritan.pokemongo.model.Pokemon.Companion.TABLE_NAME
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

@Entity(tableName = TABLE_NAME)
data class Pokemon(
    @PrimaryKey val id: Int,
    val name: String,
    @ColumnInfo(name = "default_front_image") val defaultFrontImage: String,
    val stats: List<Stat>,
    @ColumnInfo(name = "captured_date") var capturedDate: LocalDateTime? = null,
    @ColumnInfo(name = "captured_location") var capturedLocation: LatLng? = null
) {
    data class Stat(val name: String, val base: Int)

    companion object {
        const val TABLE_NAME = "pokemons"
    }
}