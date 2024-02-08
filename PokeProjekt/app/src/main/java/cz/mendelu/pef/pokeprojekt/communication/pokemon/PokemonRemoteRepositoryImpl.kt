package cz.mendelu.pef.pokeprojekt.communication.pokemon


import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.mock.ServerMock
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.PokemonData
import cz.mendelu.pef.pokeprojekt.model.PokemonSpecies
import cz.mendelu.pef.pokeprojekt.useMockedValues
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRemoteRepositoryImpl @Inject constructor(private val pokemonAPI: PokemonAPI) :
    IPokemonRemoteRepository {


    override suspend fun findPokemons(): CommunicationResult<PokemonData> {
        return processResponse(
            withContext(Dispatchers.IO) {
                pokemonAPI.findPokemons()
            }
        )
    }

    override suspend fun getPokemonById(pokemonID: Long): CommunicationResult<Pokemon> {
        return when (useMockedValues) {
            true ->
                CommunicationResult.Success(ServerMock.ditto)

            false ->
                processResponse(
                    withContext(Dispatchers.IO) {
                        pokemonAPI.getPokemonById(pokemonID)
                    }
                )
        }


    }

    override suspend fun getMoveById(moveID: Long): CommunicationResult<MoveDetailApi> {
        return when (useMockedValues) {
            true ->
                CommunicationResult.Success(ServerMock.moveDetailApi)

            false ->
                processResponse(
                    withContext(Dispatchers.IO) {
                        pokemonAPI.getMoveById(moveID)
                    }
                )
        }
    }

    override suspend fun getPokemonSpeciesById(pokemonID: Int): CommunicationResult<PokemonSpecies> {
        return when (useMockedValues) {
            true ->
                CommunicationResult.Success(ServerMock.pokemonSpecies)

            false ->
                processResponse(
                    withContext(Dispatchers.IO) {
                        pokemonAPI.getPokemonSpeciesById(pokemonID)
                    }
                )
        }
    }


}