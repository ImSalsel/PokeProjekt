@file:Suppress("SpellCheckingInspection")

package cz.mendelu.pef.pokeprojekt.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    IDataStoreRepository {


    private val Context.dataStore by preferencesDataStore(name = "pokemon_data")


    override suspend fun setPokemonToCatch(pokemon: PokemonRoom) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreConstants.KEY_POKEMON_NAME] = pokemon.name ?: "error"
            preferences[DataStoreConstants.KEY_POKEMON_ID] = pokemon.pokemonId!!
            preferences[DataStoreConstants.KEY_POKEMON_HEIGHT] = pokemon.height!!
            preferences[DataStoreConstants.KEY_POKEMON_WEIGHT] = pokemon.weight!!
            preferences[DataStoreConstants.KEY_POKEMON_MOVES] = pokemon.moves!!
            preferences[DataStoreConstants.KEY_POKEMON_FRONT_DEFAULT_SPRITE] =
                pokemon.frontDefaultSprite!!
            preferences[DataStoreConstants.KEY_POKEMON_TYPES] = pokemon.types!!

            Log.d("DataStore", "Saved Pokemon name: $pokemon")
            Log.d(
                "DataStore",
                "dataStore ${preferences[DataStoreConstants.KEY_POKEMON_ID]} name ${preferences[DataStoreConstants.KEY_POKEMON_NAME]}"
            )

        }
    }


    override fun getPokemonToCatch(): Flow<PokemonRoom> {
        val randomLevel = (1..99).random()
        return context.dataStore.data.map { preferences ->
            PokemonRoom(
                pokemonId = preferences[DataStoreConstants.KEY_POKEMON_ID],
                name = preferences[DataStoreConstants.KEY_POKEMON_NAME],
                height = preferences[DataStoreConstants.KEY_POKEMON_HEIGHT],
                weight = preferences[DataStoreConstants.KEY_POKEMON_WEIGHT],
                moves = preferences[DataStoreConstants.KEY_POKEMON_MOVES],
                frontDefaultSprite = preferences[DataStoreConstants.KEY_POKEMON_FRONT_DEFAULT_SPRITE],
                types = preferences[DataStoreConstants.KEY_POKEMON_TYPES],
                hp = randomLevel * 3,
                level = randomLevel,
                xp = 0f
            )
        }
    }

    override suspend fun setFavoritePokemonId(id: Long) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreConstants.FAVORITE_POKEMON_ID] = id

        }
    }

    override suspend fun getFavoritePokemonId(): Long? {
        val preferences = context.dataStore.data.first()
        return preferences[DataStoreConstants.FAVORITE_POKEMON_ID]
    }

    override suspend fun setSharedPokemon(string: String) {
        context.dataStore.edit { preferences ->
            preferences[DataStoreConstants.SHARED_POKEMON] = string

        }
    }

    override suspend fun getSharedPokemon(): String? {
        val preferences = context.dataStore.data.first()
        val sharedPokemon = preferences[DataStoreConstants.SHARED_POKEMON]

        context.dataStore.edit { preferences ->
            preferences[DataStoreConstants.SHARED_POKEMON] = ""
        }

        return sharedPokemon
    }

}

