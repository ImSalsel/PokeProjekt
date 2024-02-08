package cz.mendelu.pef.pokeprojekt.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.mendelu.pef.pokeprojekt.model.Pokemon

@Composable
fun PokemonItemStart(
    pokemon: Pokemon,
    onPokemonSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(4.dp),

        ) {
        AsyncImage(
            model = pokemon.sprites.frontDefault,
            contentDescription = "Pokemon Image",
            modifier = Modifier
                .width(130.dp)
                .height(130.dp)
        )
        Button(onClick = { onPokemonSelected(); }, modifier = Modifier.testTag(pokemon.name!!)) {
            pokemon.name?.let { Text(text = it) }
        }
    }
}