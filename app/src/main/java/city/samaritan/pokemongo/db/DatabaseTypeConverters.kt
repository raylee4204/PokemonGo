package city.samaritan.pokemongo.db

import androidx.room.TypeConverter
import city.samaritan.pokemongo.*
import city.samaritan.pokemongo.model.Pokemon
import com.google.android.gms.maps.model.LatLng
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.LocalDateTime

object DatabaseTypeConverters {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    @JvmStatic
    fun stringToDateTime(value: String?): LocalDateTime? {
        return value?.toLocalDateTime()
    }

    @TypeConverter
    @JvmStatic
    fun dateTimeToString(dateTime: LocalDateTime?): String? {
        return dateTime?.toIsoFormat()
    }

    @TypeConverter
    @JvmStatic
    fun stringToLatLng(value: String?): LatLng? {
        return value?.parseLatLng()
    }

    @TypeConverter
    @JvmStatic
    fun latLngToString(latLng: LatLng?): String? {
        return latLng?.toDbFormat()
    }

    @TypeConverter
    @JvmStatic
    fun stringToStats(value: String?): List<Pokemon.Stat>? {
        return value?.let { stringValue ->
            val type = Types.newParameterizedType(List::class.java, Pokemon.Stat::class.java)
            val pokemonAdapter = moshi.adapter<List<Pokemon.Stat>>(type)
            return pokemonAdapter.fromJson(stringValue)
        }
    }

    @TypeConverter
    @JvmStatic
    fun statsToString(users: List<Pokemon.Stat>?): String? {
        return users?.let { list ->
            val type = Types.newParameterizedType(List::class.java, Pokemon.Stat::class.java)
            val pokemonAdapter = moshi.adapter<List<Pokemon.Stat>>(type)
            pokemonAdapter.toJson(list)
        }
    }
}