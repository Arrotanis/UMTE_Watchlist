package cz.uhk.umte.ui.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieScreen(
    viewModel: MovieVM = getViewModel(),
    onNavigateDetail: (Long) -> Unit,
) {
    val movies = viewModel.movies.collectAsState(emptyList())
    var filter by remember { mutableStateOf("Watching") }
    Column {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(
                items = movies.value,
                key = { it.id }
            ) { movie ->
                Card(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.clickable { onNavigateDetail(movie.id) }
                        .animateItemPlacement(),
                ) {
                    Column(
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp).fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = movie.name,
                                style = MaterialTheme.typography.h4,
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                icon(movie.favorite)?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.onPrimary
                                    )
                                }
                            }
                        }
                        LinearProgressIndicator(
                            progress = (movie.timestamp / ((movie.duration + 0.0001))).toFloat(),
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.padding(8.dp).fillMaxWidth()
                        )
                    }

                }
            }
        }
        var inputText by remember { mutableStateOf("") }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var expanded by remember { mutableStateOf(false) }
            Button(
                modifier = Modifier.width(100.dp),
                onClick = {
                    expanded = true;
                }
            ) {
                Text(text = filter)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false;
                }
            )
            {
                DropdownMenuItem(onClick = {
                    filter = "Watching"
                    viewModel.changeStatus(0)
                    expanded = false
                }) {
                    Text("Watching")
                }
                DropdownMenuItem(onClick = {
                    filter = "On Hold"
                    viewModel.changeStatus(1)
                    expanded = false
                }) {
                    Text("On Hold")
                }
                DropdownMenuItem(onClick = {
                    filter = "Finished"
                    viewModel.changeStatus(2)
                    expanded = false
                }) {
                    Text("Finished")
                }
            }
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = {
                    Text(text = "Movie")
                },
                modifier = Modifier.weight(1f, true),
            )
            Button(
                modifier = Modifier.height(IntrinsicSize.Max),
                enabled = inputText.isBlank().not(),
                onClick = {
                    viewModel.addMovie(inputText)
                    inputText = ""
                },
            ) {
                Text(text = "Add")
            }
        }
    }
}

fun icon(boolean: Boolean): ImageVector? {
    if (boolean) {
        return Icons.Default.Favorite
    } else {
        return null;
    }
}