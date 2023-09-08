package com.kodeco.android.coordplot.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MapSlider(
    label: String,
    percentage: Float,
    onPercentageChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    val percentageToInt = (percentage * 100).toInt()

    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label: $percentageToInt%",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(start = 8.dp)
                .width(120.dp)
        )
        Slider(
            value = percentage,
            onValueChange = onPercentageChange
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapSliderPreview() {
    MapSlider(
        label = "X Axis",
        percentage = 0.5f,
        onPercentageChange = {}
    )
}
