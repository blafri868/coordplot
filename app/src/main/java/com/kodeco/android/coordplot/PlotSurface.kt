package com.kodeco.android.coordplot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.coordplot.ui.theme.MyApplicationTheme

@Composable
fun PlotSurface() {
    var xPercentage: Float by remember { mutableFloatStateOf(0.5f) }
    var yPercentage: Float by remember { mutableFloatStateOf(0.5f) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Map(
                xPercent = xPercentage,
                yPercent = yPercentage,
                modifier = Modifier.padding(bottom = 30.dp)
            )
            Slider(
                value = xPercentage,
                onValueChange = { value -> xPercentage = value }
            )
            Slider(
                value = yPercentage,
                onValueChange = { value -> yPercentage = value }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotSurfacePreview() {
    MyApplicationTheme {
        PlotSurface()
    }
}
