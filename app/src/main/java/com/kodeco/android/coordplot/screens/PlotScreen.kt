package com.kodeco.android.coordplot.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.coordplot.components.Map
import com.kodeco.android.coordplot.components.MapSlider
import com.kodeco.android.coordplot.ui.theme.MyApplicationTheme

@Composable
fun PlotScreen() {
    var xPercentage: Float by rememberSaveable { mutableFloatStateOf(0.5f) }
    var yPercentage: Float by rememberSaveable { mutableFloatStateOf(0.5f) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val orientation = LocalConfiguration.current.orientation

        val mapBottomPadding = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            30.dp
        } else {
            0.dp
        }

        PlotScreenContainer(orientation = orientation) {
            Map(
                xPercent = xPercentage,
                yPercent = yPercentage,
                modifier = Modifier.padding(bottom = mapBottomPadding)
            )
            Column {
                MapSlider(
                    label = "X Axis",
                    percentage = xPercentage,
                    onPercentageChange = { value -> xPercentage = value }
                )
                MapSlider(
                    label = "Y Axis",
                    percentage = yPercentage,
                    onPercentageChange = { value -> yPercentage = value }
                )
            }
        }
    }
}

// I don't think this is the best way to change between a column layout for narrower screens (Portrait Mode) and
// wider screens (Landscape mode) I think it should be based on some sort of breakpoint but couldn't figure that out
// and this works for now. Please let me know how i can improve this.
@Composable
fun PlotScreenContainer(orientation: Int, content: @Composable () -> Unit) {
    if (orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            content()
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlotSurfacePreview() {
    MyApplicationTheme {
        PlotScreen()
    }
}
