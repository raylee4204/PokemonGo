package city.samaritan.pokemongo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import city.samaritan.pokemongo.model.Pokemon

@Database(entities = [Pokemon::class], version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}