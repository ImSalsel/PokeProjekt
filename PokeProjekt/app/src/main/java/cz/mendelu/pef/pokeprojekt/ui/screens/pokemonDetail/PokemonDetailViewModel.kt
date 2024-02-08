package cz.mendelu.pef.pokeprojekt.ui.screens.pokemonDetail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.architecture.BaseViewModel
import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonRemoteRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.datastore.IDataStoreRepository
import cz.mendelu.pef.pokeprojekt.model.MoveDetail
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.screens.map.MapErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: IPokemonRepository,
    private val pokemonDataStore: IDataStoreRepository,
    private val pokemonRemoteRepositoryImpl: PokemonRemoteRepositoryImpl,

    ) : BaseViewModel() {

    val move1LiveData = MutableLiveData<MoveDetailApi>()
    val move2LiveData = MutableLiveData<MoveDetailApi>()
    val move3LiveData = MutableLiveData<MoveDetailApi>()
    val move4LiveData = MutableLiveData<MoveDetailApi>()


    var pokemonID: Long? = null

    val pokemonUIState: MutableState<UiState<PokemonRoom, PokemonDetailErrors>> =
        mutableStateOf(UiState())

    val isFavoriteLiveData = MutableLiveData<Boolean?>()


    fun checkIfFavorite(id: Long) {
        viewModelScope.launch {
            isFavoriteLiveData.value = isFavorite(id)
        }
    }

    fun fetchMoveDetails(moveIds: Long, moveLiveData: MutableLiveData<MoveDetailApi>) {
        launch {
            val result = withContext(Dispatchers.IO) {
                pokemonRemoteRepositoryImpl.getMoveById(moveIds)
            }
            when (result) {
                is CommunicationResult.CommunicationError ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonDetailErrors(R.string.no_internet_connection)
                    )

                is CommunicationResult.Error ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonDetailErrors(R.string.failed_to_load_the_list)
                    )

                is CommunicationResult.Exception ->
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = null,
                        errors = PokemonDetailErrors(R.string.unknown_error)
                    )

                is CommunicationResult.Success -> {

                    moveLiveData.value = result.data

                }
            }
        }
    }

    fun initPokemon() {
        launch {
            pokemonUIState.value = UiState(
                loading = true,
                data = repository.getPokemonById(id = pokemonID?.toInt()!!),
                errors = null
            )
            Log.d("PokedexViewModel", "pokemondetail result: ${pokemonID?.toInt()!!}")
            val moveIds = pokemonUIState.value.data?.moves?.split(",")?.mapNotNull { it.toLongOrNull() }
            if (moveIds != null && moveIds.size >= 4) {
                fetchMoveDetails(moveIds[0], move1LiveData)
                fetchMoveDetails(moveIds[1], move2LiveData)
                fetchMoveDetails(moveIds[2], move3LiveData)
                fetchMoveDetails(moveIds[3], move4LiveData)
                pokemonUIState.value.loading = false
            }

        }
        checkIfFavorite(pokemonID!!)
    }

    suspend fun isFavorite(id: Long): Boolean {
        var favoriteId = pokemonDataStore.getFavoritePokemonId()
        return favoriteId == id
    }

    fun setPokemonAsFavorite(id: Long) {
        launch {
            pokemonDataStore.setFavoritePokemonId(id)
        }
    }

}