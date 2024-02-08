package cz.mendelu.pef.pokeprojekt.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_table")
data class PokemonRoom(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,
    var pokemonId: Int?,
    val name: String?,
    val height: Int?,
    val weight: Int?,
    val moves: String?,
    val frontDefaultSprite: String?,
    val types: String?,
    var hp: Int,
    var level: Int,
    var xp: Float
)

