package cz.mendelu.pef.pokeprojekt.ui.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.mendelu.pef.pokeprojekt.database.PokemonRoom

@Composable
fun PokemonSection(pokemon: PokemonRoom?, you: Boolean, hp: Int) {
    if (pokemon != null) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (you) {
                AsyncImage(
                    model = pokemon.frontDefaultSprite,
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .width(150.dp)
                        .align(Alignment.End),

                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "${pokemon.name} LV. ${pokemon.level}",
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 16.dp)
                )
            } else {
                AsyncImage(
                    model = pokemon.frontDefaultSprite,
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .width(150.dp)
                        .align(Alignment.Start),

                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "${pokemon.name} LV. ${pokemon.level}",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp)
                )
            }

            HealthBar(health = hp, maxHp = pokemon.hp)
        }
    }
}

@Composable
fun HealthBar(health: Int, maxHp: Int) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
    ) {
        LinearProgressIndicator(
            health/maxHp.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            color = Color.Green,
            trackColor = Color.Red
        )

        Text(
            text = "${health} / ${maxHp} HP",
            modifier = Modifier.align(Alignment.Center),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}