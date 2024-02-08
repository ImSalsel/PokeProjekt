package cz.mendelu.pef.pokeprojekt.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.pokeprojekt.R

@Composable
fun TypeIcons(typesString: String) {
    val typeToIconMap = mapOf(
        "bug" to R.drawable.bug,
        "dark" to R.drawable.dark,
        "dragon" to R.drawable.dragon,
        "electric" to R.drawable.electric,
        "fairy" to R.drawable.fairy,
        "fighting" to R.drawable.fighting,
        "fire" to R.drawable.fire,
        "flying" to R.drawable.flying,
        "ghost" to R.drawable.ghost,
        "grass" to R.drawable.grass,
        "ground" to R.drawable.ground,
        "ice" to R.drawable.ice,
        "normal" to R.drawable.normal,
        "poison" to R.drawable.poison,
        "psychic" to R.drawable.psychic,
        "rock" to R.drawable.rock,
        "steel" to R.drawable.steel,
        "water" to R.drawable.water,

    )

    val typesList = typesString.split(", ")

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp,)
            .fillMaxWidth()
    ) {
        typesList.forEach { type ->
            val iconResId = typeToIconMap[type]
            if (iconResId != null) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = "type icon",
                    modifier = Modifier.size(35.dp). padding(horizontal = 1.dp)
                )
            }
        }
    }
}
