package cz.mendelu.pef.pokeprojekt.datastore

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey



object DataStoreConstants {
    val KEY_POKEMON_ID = intPreferencesKey("pokemon_id")
    val KEY_POKEMON_NAME = stringPreferencesKey("pokemon_name")
    val KEY_POKEMON_HEIGHT = intPreferencesKey("pokemon_height")
    val KEY_POKEMON_WEIGHT = intPreferencesKey("pokemon_weight")
    val KEY_POKEMON_MOVES = stringPreferencesKey("pokemon_moves")
    val KEY_POKEMON_FRONT_DEFAULT_SPRITE = stringPreferencesKey("front_default_sprite")
    val KEY_POKEMON_TYPES = stringPreferencesKey("pokemon_types")

    val FAVORITE_POKEMON_ID = longPreferencesKey("favorite_pokemon_id")
    val SHARED_POKEMON = stringPreferencesKey("shared_pokemon")

}