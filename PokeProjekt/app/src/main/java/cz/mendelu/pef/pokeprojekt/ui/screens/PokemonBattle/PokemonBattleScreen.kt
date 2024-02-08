package cz.mendelu.pef.pokeprojekt.ui.screens.PokemonBattle

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.elements.BaseScreen
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.model.Move
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi
import cz.mendelu.pef.pokeprojekt.ui.elements.BattleResultPopup
import cz.mendelu.pef.pokeprojekt.ui.elements.MoveButton
import cz.mendelu.pef.pokeprojekt.ui.elements.MovesGrid
import cz.mendelu.pef.pokeprojekt.ui.elements.PokemonSection
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.ListOfPokemonDestination
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.MapScreenContentDestination
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.MapScreenDestination

@Destination
@Composable
fun PokemonBattleScreen(navigator: DestinationsNavigator) {

    val viewModel = hiltViewModel<PokemonBattleViewModel>()

    val uiState: MutableState<UiState<PokemonRoom, PokemonBattleErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.pokemonUIState.value.let {
        uiState.value = it
    }

    if (uiState.value.loading) {
        viewModel.loadPokemonFromDataStore()
    }

    viewModel.opponentHp.value = uiState.value.data?.hp ?: 100
    viewModel.playerHp.value = viewModel.favoritePokemon?.hp ?: 100

    BaseScreen(
        topBarText = stringResource(R.string.pokemon_battle),
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
    ) {
        PokemonBattleScreenContent(
            paddingValues = it,
            uiState = uiState,
            viewModel = viewModel,
            navigator = navigator
        )
    }
}

@Composable
fun PokemonBattleScreenContent(
    paddingValues: PaddingValues,
    uiState: MutableState<UiState<PokemonRoom, PokemonBattleErrors>>,
    viewModel: PokemonBattleViewModel,
    navigator: DestinationsNavigator

) {



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFF51A3D0))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PokemonSection(pokemon = uiState.value.data, you = false, hp = viewModel.opponentHp.value)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp), horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.versus_icon),
                contentDescription = "Versus",
                modifier = Modifier
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),

            ) {
            PokemonSection(
                pokemon = viewModel.favoritePokemon,
                you = true,
                hp = viewModel.playerHp.value
            )
        }

        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {

            MovesGrid(
                listOf(
                    viewModel.move1LiveData,
                    viewModel.move2LiveData,
                    viewModel.move3LiveData,
                    viewModel.move4LiveData
                )
            ) { move ->
                viewModel.onMoveUsed(move)
            }


            BattleResultPopup(
                isBattleWon = viewModel.isBattleWon.value,
                onDismiss = {
                    viewModel.isBattleWon.value = false
                    navigator.popBackStack()
                    navigator.navigate(ListOfPokemonDestination)
                }
            )

        }
    }
}


