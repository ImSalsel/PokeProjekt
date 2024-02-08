package cz.mendelu.pef.pokeprojekt.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi


@Composable
fun MovesGrid(
    movesLiveData: List<LiveData<MoveDetailApi>>,
    onMoveSelected: (MoveDetailApi) -> Unit
) {
    Column {
        for (i in movesLiveData.indices step 2) {
            Row {
                MoveButton(
                    move = movesLiveData.getOrNull(i)?.observeAsState()?.value,
                    onMoveSelected = {
                        movesLiveData.getOrNull(i)?.value?.let { move ->
                            onMoveSelected(
                                move
                            )
                        }
                    }
                )
                MoveButton(
                    move = movesLiveData.getOrNull(i + 1)?.observeAsState()?.value,
                    onMoveSelected = {
                        movesLiveData.getOrNull(i + 1)?.value?.let { move ->
                            onMoveSelected(
                                move
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MoveButton(move: MoveDetailApi?, onMoveSelected: () -> Unit) {
    move?.let {
        Button(
            onClick = { onMoveSelected() },
            modifier = Modifier
                .height(100.dp)
                .width(200.dp)
                .padding(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6AB4DD))
        ) {
            Column {
                Text(text = it.name.orEmpty(), fontWeight = FontWeight.Bold)
                Text(text = "Power: ${it.power ?: "N/A"}")
                Text(text = "Accuracy: ${it.accuracy ?: "N/A"}%")
            }
        }
    }
}
