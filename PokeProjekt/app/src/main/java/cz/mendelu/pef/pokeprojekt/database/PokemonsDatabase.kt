package cz.mendelu.pef.pokeprojekt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [PokemonRoom::class], version = 4, exportSchema = true)
abstract class PokemonsDatabase : RoomDatabase() {

    abstract fun pokemonsDao(): PokemonsDao

    companion object {
        private var INSTANCE: PokemonsDatabase? = null
        fun getDatabase(context: Context): PokemonsDatabase {
            if (INSTANCE == null) {
                synchronized(PokemonsDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            PokemonsDatabase::class.java, "pokemons_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

    }
}

