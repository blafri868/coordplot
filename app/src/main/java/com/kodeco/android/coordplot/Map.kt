package com.kodeco.android.coordplot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.coordplot.ui.theme.MyApplicationTheme

@Composable
fun Map(xPercent: Float, yPercent: Float, modifier: Modifier = Modifier) {
    val mapSize = 300
    val pointSize = 36
    val xOffset = (xPercent * mapSize) - (pointSize / 2)
    val yOffset = (yPercent * mapSize) - (pointSize / 2)

    Box(
        modifier = modifier
            .size(mapSize.dp)
            .background(Color.Blue)
    ) {
        Box(
            modifier = Modifier
                .offset(xOffset.dp, yOffset.dp)
                .clip(shape = CircleShape)
                .size(pointSize.dp)
                .background(Color.Green)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapPreview() {
    MyApplicationTheme {
        Map(xPercent = 0.5f, yPercent = 0.5f)
    }
}
