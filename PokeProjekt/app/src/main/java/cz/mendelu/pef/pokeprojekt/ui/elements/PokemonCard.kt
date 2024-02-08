package cz.mendelu.pef.pokeprojekt.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom
import cz.mendelu.pef.pokeprojekt.model.Pokemon
import cz.mendelu.pef.pokeprojekt.ui.screens.destinations.PokemonDetailDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonCard(pokemon: PokemonRoom, navigator: DestinationsNavigator) {


    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF6AB4DD)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF6AB4DD), RoundedCornerShape(16.dp)),
        onClick = { navigator.navigate(PokemonDetailDestination(id = pokemon.id)) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color(0xFF6AB4DD))
        ) {

            AsyncImage(
                model = pokemon.frontDefaultSprite,
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            pokemon.name?.let { 
                Text(
                    text = it + " Lv. " + pokemon.level.toString(),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

    }
}