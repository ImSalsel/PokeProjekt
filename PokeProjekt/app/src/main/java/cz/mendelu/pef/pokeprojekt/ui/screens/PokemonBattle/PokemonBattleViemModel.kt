package cz.mendelu.pef.pokeprojekt.ui.screens.PokemonBattle

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.architecture.BaseViewModel
import cz.mendelu.pef.pokeprojekt.architecture.CommunicationResult
import cz.mendelu.pef.pokeprojekt.communication.pokemon.PokemonRemoteRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.IPokemonRepository
import cz.mendelu.pef.pokeprojekt.database.PokemonRepositoryImpl
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.datastore.DataStoreRepositoryImpl
import cz.mendelu.pef.pokeprojekt.datastore.IDataStoreRepository
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.screens.map.MapErrors
import cz.mendelu.pef.pokeprojekt.ui.screens.pokemonDetail.PokemonDetailErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class PokemonBattleViewModel @Inject constructor(
    private val pokemonDataStore: IDataStoreRepository,
    private val pokemonRemoteRepositoryImpl: PokemonRemoteRepositoryImpl,
    private val repository: IPokemonRepository
) : BaseViewModel() {

    var isBattleWon = mutableStateOf(false)

    var playerHp = mutableIntStateOf(0)
    var opponentHp = mutableIntStateOf(0)

    val move1LiveData = MutableLiveData<MoveDetailApi>()
    val move2LiveData = MutableLiveData<MoveDetailApi>()
    val move3LiveData = MutableLiveData<MoveDetailApi>()
    val move4LiveData = MutableLiveData<MoveDetailApi>()

    val pokemonUIState: MutableState<UiState<PokemonRoom, PokemonBattleErrors>> =
        mutableStateOf(UiState())

    var favoritePokemon: PokemonRoom? = null

    suspend fun getFavoritePokemonId(): Long? {
        return pokemonDataStore.getFavoritePokemonId()
        Log.d("BattleViewModel", "favoritePokemon ${favoritePokemon}")
    }

    fun onMoveUsed(move: MoveDetailApi) {
        viewModelScope.launch {
            val hitChance = Random.nextInt(100)
            if (hitChance <= (move.accuracy ?: 100)) {

                val damageDealt = (move.power ?: 0)
                opponentHp.value -= damageDealt
                Log.d(
                    "PokemonBattle",
                    "move ${move.name} and hp ${pokemonUIState.value.data?.hp} and damage${damageDealt}"
                )
                if (opponentHp.value <= 0) {
                    repository.insert(pokemonUIState.value.data!!)
                    isBattleWon.value = true
                }

            } else {

            }
        }
    }


    fun fetchMoveDetails(moveIds: Long, moveLiveData: MutableLiveData<MoveDetailApi>) {
        launch {
            val result = withContext(Dispatchers.IO) {
                pokemonRemoteRepositoryImpl.getMoveById(moveIds)
            }
            when (result) {
                is CommunicationResult.CommunicationError -> {}

                is CommunicationResult.Error -> {}

                is CommunicationResult.Exception -> {}

                is CommunicationResult.Success -> {
                    moveLiveData.value = result.data

                }
            }
        }
    }

    fun loadPokemonFromDataStore() {
        viewModelScope.launch {
            val pokemonRoomLiveData = pokemonDataStore.getPokemonToCatch().asLiveData()
            favoritePokemon = getFavoritePokemonId()?.let { repository.getPokemonById(it.toInt()) }

            val moveIds =
                favoritePokemon?.moves?.split(",")?.mapNotNull { it.toLongOrNull() }
            if (moveIds != null && moveIds.size >= 4) {
                fetchMoveDetails(moveIds[0], move1LiveData)
                fetchMoveDetails(moveIds[1], move2LiveData)
                fetchMoveDetails(moveIds[2], move3LiveData)
                fetchMoveDetails(moveIds[3], move4LiveData)
            }
            Log.d("PokemonBattle", "moveIds ${moveIds}")
            pokemonRoomLiveData.observeForever { pokemonRoom ->
                pokemonRoom?.let {
                    pokemonUIState.value = UiState(
                        loading = false,
                        data = it,
                        errors = null
                    )
                }
            }
            Log.d("PokemonBattle", "opponent ${pokemonUIState.value.data}")
        }
    }
}