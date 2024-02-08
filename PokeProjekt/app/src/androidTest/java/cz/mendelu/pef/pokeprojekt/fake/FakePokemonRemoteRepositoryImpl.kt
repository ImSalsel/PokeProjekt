package cz.mendelu.pef.pokeprojekt.fake

import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.communication.pokemon.IPokemonRemoteRepository
import cz.mendelu.pef.pokeprojekt.mock.ServerMock
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.PokemonData
import cz.mendelu.pef.pokeprojekt.model.PokemonSpecies
import javax.inject.Inject


class FakePokemonRemoteRepositoryImpl @Inject constructor() : IPokemonRemoteRepository {


    override suspend fun findPokemons(): CommunicationResult<PokemonData> {
        return CommunicationResult.Exception(exception = Throwable())
    }

    override suspend fun getPokemonById(pokemonID: Long): CommunicationResult<Pokemon> {
        return CommunicationResult.Success(
            ServerMock.starters[pokemonID.toInt()]
        )
    }

    override suspend fun getMoveById(moveID: Long): CommunicationResult<MoveDetailApi> {
        return CommunicationResult.Success(
            ServerMock.moveDetailApi
        )
    }

    override suspend fun getPokemonSpeciesById(pokemonID: Int): CommunicationResult<PokemonSpecies> {
        return CommunicationResult.Success(
            ServerMock.pokemonSpecies
        )
    }
}