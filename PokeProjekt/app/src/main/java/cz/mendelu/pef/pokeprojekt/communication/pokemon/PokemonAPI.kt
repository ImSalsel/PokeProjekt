package cz.mendelu.pef.pokeprojekt.communication.pokemon


import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.PokemonData
import cz.mendelu.pef.pokeprojekt.model.PokemonSpecies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {

    @GET("pokemon")
    suspend fun findPokemons(): Response<PokemonData>

    @GET("pokemon/{pokemonID}")
    suspend fun getPokemonById(@Path("pokemonID") pokemonID: Long): Response<Pokemon>


    @GET("move/{moveID}")
    suspend fun getMoveById(@Path("moveID")moveID: Long): Response<MoveDetailApi>

    @GET("pokemon-species/{pokemonID}")
    suspend fun getPokemonSpeciesById(@Path("pokemonID") pokemonID: Int): Response<PokemonSpecies>

}