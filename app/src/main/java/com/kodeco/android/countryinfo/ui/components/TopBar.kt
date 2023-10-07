package com.kodeco.android.countryinfo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R

@Composable
fun TopBar(tapCount: Int, backCount: Int, onRefreshClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.tap_count_label, tapCount))
        Button(onClick = onRefreshClick) {
            Text(text = stringResource(R.string.refresh_button_text))
        }
        Text(text = stringResource(R.string.back_count_label, backCount))
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(tapCount = 5, backCount = 5) {}
}
