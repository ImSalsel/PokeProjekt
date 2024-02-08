package cz.mendelu.pef.pokeprojekt.communication.pokemon

import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.architecture.IBaseRemoteRepository
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.PokemonData
import cz.mendelu.pef.pokeprojekt.model.PokemonSpecies


interface IPokemonRemoteRepository : IBaseRemoteRepository {

    suspend fun findPokemons(): CommunicationResult<PokemonData>

    suspend fun getPokemonById(pokemonID: Long): CommunicationResult<Pokemon>

    suspend fun getMoveById(moveID: Long): CommunicationResult<MoveDetailApi>

    suspend fun getPokemonSpeciesById(pokemonID: Int): CommunicationResult<PokemonSpecies>
}