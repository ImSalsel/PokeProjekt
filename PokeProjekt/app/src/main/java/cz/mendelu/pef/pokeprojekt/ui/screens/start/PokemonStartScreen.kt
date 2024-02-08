package cz.mendelu.pef.pokeprojekt.ui.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.model.UiState
import cz.mendelu.pef.pokeprojekt.ui.elements.BaseScreen
import cz.mendelu.pef.pokeprojekt.ui.elements.PokemonItemStart

const val TestTagChooseButton = "TestTagChooseButton"


@Destination(route = "start")
@Composable
fun PokemonStartScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = hiltViewModel<PokemonStartViewModel>()

    val uiState: MutableState<UiState<List<Pokemon>, PokemonStartErrors>> =
        rememberSaveable {
            mutableStateOf(UiState())
        }

    viewModel.pokemonUIState.value.let {
        uiState.value = it

    }

    BaseScreen(
        topBarText = null,
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
    ) {
        PokemonStartScreenContent(
            paddingValues = it,
            uiState = uiState,
            viewModel = viewModel,
            navigator = navigator
        )
    }


}

@Composable
fun PokemonStartScreenContent(
    paddingValues: PaddingValues,
    uiState: MutableState<UiState<List<Pokemon>, PokemonStartErrors>>,
    viewModel: PokemonStartViewModel,
    navigator: DestinationsNavigator
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF51A3D0))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.chose_your_fist_pokemon), modifier = Modifier, fontSize = 24.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            uiState.value.data?.forEach { pokemon ->
                PokemonItemStart(
                    pokemon = pokemon,
                    onPokemonSelected = { viewModel.savePokemon(pokemon);navigator.navigate("map") },
                    modifier = Modifier
                    )

            }
        }
    }
}


