package cz.uhk.umte.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import cz.uhk.umte.ui.*

@Composable
fun HomeScreen(
    parentController: NavHostController,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Media Tracker",
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h3
        )
        Divider()
        Button(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            onClick = {
                parentController.navigateMovieScreen()
            }
        ) {
            Text(
                text = "Movies",
                style = MaterialTheme.typography.h4
            )
        }
        Button(
            modifier = Modifier.padding(16.dp).fillMaxWidth(0.9F),
            onClick = {
                parentController.navigateSeriesScreen()
            }
        ) {
            Text(
                text = "Series",
                style = MaterialTheme.typography.h4
            )
        }
        Button(
            modifier = Modifier.padding(16.dp).fillMaxWidth(0.8F),
            onClick = {
                parentController.navigateBookScreen()
            }
        ) {
            Text(
                text = "Books",
                style = MaterialTheme.typography.h4
            )
        }
    }
}