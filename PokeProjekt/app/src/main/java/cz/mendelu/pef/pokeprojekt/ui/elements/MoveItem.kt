package cz.mendelu.pef.pokeprojekt.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.mendelu.pef.pokeprojekt.R
import cz.mendelu.pef.pokeprojekt.model.MoveDetailApi

@Composable
fun MoveItem(moveDetail: MoveDetailApi) {
    var expanded by remember { mutableStateOf(false) }

    val accuracy: Int = moveDetail.accuracy ?: 100

    val power: Int = moveDetail.power ?: 0

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color(0xFF6AB4DD), RoundedCornerShape(16.dp))
            .clickable { expanded = !expanded }
            .testTag(moveDetail.name!!)
    ) {
        if (!expanded) {
            moveDetail.name?.let {
                Text(
                    text = it, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                        .padding(horizontal = 6.dp)
                )
            }
            Text(
                text = stringResource(R.string.power) + "$power",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            )
            Text(
                text = stringResource(R.string.accuracy) + "$accuracy",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            )
        } else {
            Text(
                text = stringResource(R.string.move_description) + "${moveDetail.englishFlavorText}",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .fillMaxWidth()
            )
        }
    }
}