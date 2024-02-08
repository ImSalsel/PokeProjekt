package cz.mendelu.pef.pokeprojekt.ui.elements

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import cz.mendelu.pef.pokeprojekt.R

@Composable
fun BattleResultPopup(isBattleWon: Boolean, onDismiss: () -> Unit) {
    if (isBattleWon) {
        AlertDialog(
            onDismissRequest = { onDismiss },
            title = { Text(text = stringResource(R.string.battle_result)) },
            text = { Text(text = stringResource(R.string.congratulations_you_have_won_the_battle)) },
            confirmButton = {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6AB4DD))
                ) {
                    Text(stringResource(R.string.pokemons))
                }
            }
        )
    }
}