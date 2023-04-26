package cz.uhk.umte.ui.series

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SeriesDetailScreen(
    seriesId: Long?,
    viewModel: SeriesDetailVM = getViewModel() {
        parametersOf(seriesId)
    },
    onNavigateDetail: () -> Unit,
) {
    val detail = viewModel.series.collectAsState(null)
    val dialogShown = remember {
        mutableStateOf(false)
    }
    val updatedSeries = detail.value
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        detail.value?.let { seriesEntity ->
            Row {
                Text(text = "Id: ")
                Text(text = seriesEntity.id.toString())
            }
            Row {
                Text(text = "Updated: ")
                Text(text = seriesEntity.changeDate.toString())
            }
            Column {
                var inputText by remember { mutableStateOf(seriesEntity.name) }
                OutlinedTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        viewModel.updateSeries(inputText)
                    },
                    label = {
                        Text(text = "Series Name")
                    },
                )
            }
            Column {
                var inputText by remember { mutableStateOf(seriesEntity.description) }
                OutlinedTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        if (updatedSeries != null) {
                            updatedSeries.description = inputText
                            viewModel.updateSeries(updatedSeries)
                        }
                    },
                    label = {
                        Text(text = "Series Description")
                    },
                )
            }
            var sliderValueDuration by remember {
                mutableStateOf(seriesEntity.duration)
            }
            var sliderValueTimestamp by remember {
                mutableStateOf(seriesEntity.timestamp)
            }
            Column {
                Column {
                    Slider(
                        value = sliderValueDuration.toFloat(),
                        onValueChange = {
                            sliderValueDuration = it.toInt()
                            if (sliderValueTimestamp > sliderValueDuration) {
                                sliderValueTimestamp = sliderValueDuration
                            }
                        },
                        onValueChangeFinished = {
                            if (updatedSeries != null) {
                                updatedSeries.duration = sliderValueDuration
                                updatedSeries.timestamp = sliderValueTimestamp
                                viewModel.updateSeries(updatedSeries)
                            }

                        },
                        valueRange = 0f..300f
                    )
                }
                Column { Text(text = "Episode Duration: " + sliderValueDuration / 60 + "h " + sliderValueDuration.mod(60) + "m") }
            }
            Column {

                Column {
                    Slider(
                        value = sliderValueTimestamp.toFloat(),
                        onValueChange = {
                            sliderValueTimestamp = it.toInt()
                        },
                        onValueChangeFinished = {
                            if (updatedSeries != null) {
                                updatedSeries.timestamp = sliderValueTimestamp
                                viewModel.updateSeries(updatedSeries)
                            }

                        },
                        valueRange = 0f..sliderValueDuration.toFloat()
                    )
                }
                Column { Text(text = "Timestamp: " + sliderValueTimestamp / 60 + "h " + sliderValueTimestamp.mod(60) + "m") }
            }
            var season by remember {
                mutableStateOf(seriesEntity.season)
            }
            var episode by remember {
                mutableStateOf(seriesEntity.episode)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Season: " + season)
                Spacer(modifier = Modifier.width(8.dp))
                if (updatedSeries != null) {
                    Button(onClick = {
                        season++
                        episode=1
                        sliderValueTimestamp=0
                        updatedSeries.timestamp=sliderValueTimestamp
                        updatedSeries.season=season
                        updatedSeries.episode=episode
                        viewModel.updateSeries(updatedSeries)
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (updatedSeries != null && season>1) {
                    Button(onClick = {
                        season--
                        episode=1
                        sliderValueTimestamp=0
                        updatedSeries.timestamp=sliderValueTimestamp
                        updatedSeries.season=season
                        updatedSeries.episode=episode
                        viewModel.updateSeries(updatedSeries)
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Episode: " + episode)
                Spacer(modifier = Modifier.width(8.dp))
                if (updatedSeries != null) {
                    Button(onClick = {
                        episode++
                        sliderValueTimestamp=0
                        updatedSeries.timestamp=sliderValueTimestamp
                        updatedSeries.episode=episode
                        viewModel.updateSeries(updatedSeries)
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (updatedSeries != null && episode>1) {
                    Button(onClick = {
                        episode--
                        sliderValueTimestamp=0
                        updatedSeries.timestamp=sliderValueTimestamp
                        updatedSeries.episode=episode
                        viewModel.updateSeries(updatedSeries)
                    }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Favorite: ")
                var checkboxValue by remember {
                    mutableStateOf(seriesEntity.favorite)
                }
                Column {
                    Checkbox(
                        checked = checkboxValue,
                        onCheckedChange = {
                            checkboxValue = it
                            if (updatedSeries != null) {
                                updatedSeries.favorite = checkboxValue
                                viewModel.updateSeries(updatedSeries)
                            }
                        }
                    )
                }
            }

            Row(Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
                var state by remember { mutableStateOf(seriesEntity.status) }
                Text(text = "Watching:")
                RadioButton(
                    selected = state == 0,
                    onClick = {
                        state = 0
                        if (updatedSeries != null) {
                            updatedSeries.status = state
                            viewModel.updateSeries(updatedSeries)
                        }
                    }
                )
                Text(text = "On Hold:")
                RadioButton(
                    selected = state == 1,
                    onClick = {
                        state = 1
                        if (updatedSeries != null) {
                            updatedSeries.status = state
                            viewModel.updateSeries(updatedSeries)
                        }
                    }
                )
                Text(text = "Finished:")
                RadioButton(
                    selected = state == 2,
                    onClick = {
                        state = 2
                        if (updatedSeries != null) {
                            updatedSeries.status = state
                            viewModel.updateSeries(updatedSeries)
                        }
                    }
                )
            }
            Button(onClick = {
                dialogShown.switch()
            }) {
                Text(text = "Delete")
            }
            if (dialogShown.value) {
                AlertDialog(
                    onDismissRequest = { dialogShown.switch() },
                    buttons = {
                        TextButton(onClick = {
                            viewModel.deleteSeries()
                            onNavigateDetail()
                        }) {
                            Text("Delete")
                        }
                        TextButton(onClick = { dialogShown.switch() }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("Delete confirmation") },
                    text = { Text("Are you sure you want to delete this?") },
                )
            }
        }
    }
}

fun MutableState<Boolean>.switch() {
    value = value.not()
}
