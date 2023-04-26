package cz.uhk.umte.ui.series

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
fun SeriesScreen(
    viewModel: SeriesVM = getViewModel(),
    onNavigateDetail: (Long) -> Unit,
) {
    var series = viewModel.series.collectAsState(emptyList())
    Column {
        LazyColumn(
            modifier = Modifier.background(MaterialTheme.colors.secondaryVariant)
                .fillMaxWidth()
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(
                items = series.value,
                key = { it.id }
            ) { series ->
                Card(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.clickable { onNavigateDetail(series.id) }
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
                                text = series.name,
                                style = MaterialTheme.typography.h4,
                            )
                            Column(horizontalAlignment = Alignment.End) {
                                icon(series.favorite)?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.onPrimary
                                    )
                                }
                                Text(
                                    text = "S"+series.season.toString()+" E"+series.episode.toString(),
                                )
                            }
                        }
                        LinearProgressIndicator(
                            progress = (series.timestamp / ((series.duration + 0.0001))).toFloat(),
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.padding(8.dp).fillMaxWidth()
                        )
                    }
                }
            }
        }
        var inputText by remember { mutableStateOf(viewModel.search) }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(IntrinsicSize.Max).padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            var expanded by remember { mutableStateOf(false) }
            Button(
                modifier = Modifier.width(100.dp).fillMaxHeight(0.7F),
                onClick = {
                    expanded = true;
                }
            ) {
                Text(text = viewModel.filter)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false;
                }
            )
            {
                DropdownMenuItem(onClick = {
                    viewModel.filter="Watching"
                    viewModel.status = 0
                    viewModel.refresh()
                    expanded = false
                }) {
                    Text("Watching")
                }
                DropdownMenuItem(onClick = {
                    viewModel.filter="On Hold"
                    viewModel.status = 1
                    viewModel.refresh()
                    expanded = false
                }) {
                    Text("On Hold")
                }
                DropdownMenuItem(onClick = {
                    viewModel.filter="Finished"
                    viewModel.status = 2
                    viewModel.refresh()
                    expanded = false
                }) {
                    Text("Finished")
                }
            }
            OutlinedTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    viewModel.search = inputText
                    viewModel.refresh()
                },
                label = {
                    Text(text = "Search")
                },
                modifier = Modifier.weight(1f, true),
            )
            Button(
                modifier = Modifier.fillMaxHeight(0.7F),
                enabled = inputText.isBlank().not(),
                onClick = {
                    viewModel.addSeries(inputText)
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
