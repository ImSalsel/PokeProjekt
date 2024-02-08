package cz.mendelu.pef.pokeprojekt.ui.screens.pokemonList

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.architecture.BaseViewModel
import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonRemoteRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.PokemonData
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.screens.map.MapErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ListOfPokemonViewModel @Inject constructor(
    private val pokemonRemoteRepositoryImpl: PokemonRemoteRepositoryImpl,
    private val repository: IPokemonRepository

) : BaseViewModel() {

    val pokemonUIState: MutableState<UiState<ListOfPokemonData, ListOfPokemonErrors>> =
        mutableStateOf(UiState())

    private var pokemonData = ListOfPokemonData()

    init {
        launch {
            repository.getAll().collect {
                pokemonData.pokemons = it
                pokemonUIState.value = UiState(data = pokemonData, errors = null)
                Log.d("listofpokemon","${pokemonData.pokemons}")
            }
        }
    }

    fun findPokemons() {
        val rnds = (1..493).random()

        launch {
            val result = withContext(Dispatchers.IO) {
                pokemonRemoteRepositoryImpl.getPokemonById(rnds.toLong())
            }
            when (result) {
                is CommunicationResult.CommunicationError ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfPokemonErrors(R.string.no_internet_connection)
                    )

                is CommunicationResult.Error ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfPokemonErrors(R.string.failed_to_load_the_list)
                    )

                is CommunicationResult.Exception ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ListOfPokemonErrors(R.string.unknown_error)
                    )

                is CommunicationResult.Success -> {
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = pokemonData,
                        errors = null
                    )
                    val randomLevel = (1..99).random()
                    val moveIds = result.data.moves.shuffled().take(4).mapNotNull { move ->
                        move.move.url.split("/").dropLast(1).lastOrNull()
                    }.joinToString(",")


                    val pokemonRoom = PokemonRoom(
                        pokemonId = result.data.id,
                        name = result.data.name,
                        height = result.data.height,
                        weight = result.data.weight,
                        moves = moveIds,
                        frontDefaultSprite = result.data.sprites.frontDefault,
                        types = result.data.types.joinToString(", ") { it.type.name },
                        hp = randomLevel * 3,
                        level = randomLevel,
                        xp = 0f

                    )
                    repository.insert(pokemonRoom)
                    Log.d("MapViewModel", "moveIds ${moveIds}")
                }
            }
        }


    }
}