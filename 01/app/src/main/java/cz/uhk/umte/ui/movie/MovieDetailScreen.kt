package cz.uhk.umte.ui.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailScreen(
    movieId: Long?,
    viewModel: MovieDetailVM = getViewModel() {
        parametersOf(movieId)
    },
    onNavigateDetail: () -> Unit,
) {
    val detail = viewModel.movie.collectAsState(null)
    val dialogShown = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        detail.value?.let { movieEntity ->
            Row {
                Text(text = "Id: ")
                Text(text = movieEntity.id.toString())
            }
            Row {
                Text(text = "Updated: ")
                Text(text = movieEntity.changeDate.toString())
            }
            Column {
                    var inputText by remember { mutableStateOf(movieEntity.name) }
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = {
                            inputText = it
                            viewModel.updateMovie(inputText)
                        },
                        label = {
                            Text(text = "Movie Name")
                        },
                    )
            }
            Column {
                    var inputText by remember { mutableStateOf(movieEntity.description) }
                    val updatedMovie = detail.value
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = {
                            inputText = it
                            if (updatedMovie != null) {
                                updatedMovie.description = inputText
                                viewModel.updateMovie(updatedMovie)
                            }
                        },
                        label = {
                            Text(text = "Movie Description")
                        },
                    )
            }
            var sliderValueDuration by remember {
                mutableStateOf(movieEntity.duration)
            }
            var sliderValueTimestamp by remember {
                mutableStateOf(movieEntity.timestamp)
            }
            Column {
                    val updatedMovie = detail.value
                    Column {
                        Slider(
                            value = sliderValueDuration.toFloat(),
                            onValueChange = {
                                sliderValueDuration = it.toInt()
                                if  (sliderValueTimestamp>sliderValueDuration) {
                                    sliderValueTimestamp=sliderValueDuration
                                }
                            },
                            onValueChangeFinished = {
                                if (updatedMovie != null) {
                                    updatedMovie.duration = sliderValueDuration
                                    updatedMovie.timestamp = sliderValueTimestamp
                                    viewModel.updateMovie(updatedMovie)
                                }

                            },
                            valueRange = 0f..300f
                        )
                    }
                    Column { Text(text = "Movie Duration: " + sliderValueDuration / 60 + "h " + sliderValueDuration.mod(60) + "m") }
            }
            Column {

                    val updatedMovie = detail.value
                    Column {
                        Slider(
                            value = sliderValueTimestamp.toFloat(),
                            onValueChange = {
                                sliderValueTimestamp = it.toInt()
                            },
                            onValueChangeFinished = {
                                if (updatedMovie != null) {
                                    updatedMovie.timestamp = sliderValueTimestamp
                                    viewModel.updateMovie(updatedMovie)
                                }

                            },
                            valueRange = 0f..sliderValueDuration.toFloat()
                        )
                    }
                    Column { Text(text = "Timestamp: " + sliderValueTimestamp / 60 + "h " + sliderValueTimestamp.mod(60) + "m") }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Favorite: ")
                    var checkboxValue by remember {
                        mutableStateOf(movieEntity.favorite)
                    }
                    val updatedMovie = detail.value
                    Column {
                        Checkbox(
                            checked = checkboxValue,
                            onCheckedChange = {
                                checkboxValue = it
                                if (updatedMovie != null) {
                                    updatedMovie.favorite = checkboxValue
                                    viewModel.updateMovie(updatedMovie)
                                }
                            }
                        )
                    }
            }

            Row(Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
                    var state by remember { mutableStateOf(movieEntity.status) }
                    val updatedMovie = detail.value
                    Text(text = "Watching:")
                    RadioButton(
                        selected = state == 0,
                        onClick = {
                            state = 0
                            if (updatedMovie != null) {
                                updatedMovie.status = state
                                viewModel.updateMovie(updatedMovie)
                            }
                        }
                    )
                    Text(text = "On Hold:")
                    RadioButton(
                        selected = state == 1,
                        onClick = {
                            state = 1
                            if (updatedMovie != null) {
                                updatedMovie.status = state
                                viewModel.updateMovie(updatedMovie)
                            }
                        }
                    )
                    Text(text = "Finished:")
                    RadioButton(
                        selected = state == 2,
                        onClick = {
                            state = 2
                            if (updatedMovie != null) {
                                updatedMovie.status = state
                                viewModel.updateMovie(updatedMovie)
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
                            viewModel.deleteMovie()
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
