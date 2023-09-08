package com.kodeco.android.coordplot.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.coordplot.R
import com.kodeco.android.coordplot.ui.theme.MyApplicationTheme

@Composable
fun Map(xPercent: Float, yPercent: Float, modifier: Modifier = Modifier) {
    val mapSize = 300
    val pointSize = 20
    val xOffset = (xPercent * mapSize) - (pointSize * xPercent)
    val yOffset = (yPercent * mapSize) - (pointSize * yPercent)

    Box(
        modifier = modifier
            .size(mapSize.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.map_background),
            contentDescription = stringResource(R.string.map_background_description)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_x_mark),
            contentDescription = stringResource(R.string.map_pointer_description),
            modifier = Modifier
                .size(pointSize.dp)
                .offset(xOffset.dp, yOffset.dp)
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
