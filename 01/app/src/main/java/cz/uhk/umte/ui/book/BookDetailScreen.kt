package cz.uhk.umte.ui.book

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
fun BookDetailScreen(
    bookId: Long?,
    viewModel: BookDetailVM = getViewModel() {
        parametersOf(bookId)
    },
    onNavigateDetail: () -> Unit,
) {
    val detail = viewModel.book.collectAsState(null)
    val dialogShown = remember {
        mutableStateOf(false)
    }
    val updatedBook = detail.value
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        detail.value?.let { bookEntity ->
            Row {
                Text(text = "Id: ")
                Text(text = bookEntity.id.toString())
            }
            Row {
                Text(text = "Updated: ")
                Text(text = bookEntity.changeDate.toString())
            }
            Column {
                var inputText by remember { mutableStateOf(bookEntity.name) }
                OutlinedTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        viewModel.updateBook(inputText)
                    },
                    label = {
                        Text(text = "Book Name")
                    },
                )
            }
            Column {
                var inputText by remember { mutableStateOf(bookEntity.description) }
                OutlinedTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        if (updatedBook != null) {
                            updatedBook.description = inputText
                            viewModel.updateBook(updatedBook)
                        }
                    },
                    label = {
                        Text(text = "Book Description")
                    },
                )
            }
            Column {
                var inputText by remember { mutableStateOf(bookEntity.author) }
                OutlinedTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        if (updatedBook != null) {
                            updatedBook.author = inputText
                            viewModel.updateBook(updatedBook)
                        }
                    },
                    label = {
                        Text(text = "Book Author")
                    },
                )
            }
            var sliderValueDuration by remember {
                mutableStateOf(bookEntity.pages)
            }
            var sliderValueBookstamp by remember {
                mutableStateOf(bookEntity.bookstamp)
            }
            Column {
                Column {
                    Slider(
                        value = sliderValueDuration.toFloat(),
                        onValueChange = {
                            sliderValueDuration = it.toInt()
                            if (sliderValueBookstamp > sliderValueDuration) {
                                sliderValueBookstamp = sliderValueDuration
                            }
                        },
                        onValueChangeFinished = {
                            if (updatedBook != null) {
                                updatedBook.pages = sliderValueDuration
                                updatedBook.bookstamp = sliderValueBookstamp
                                viewModel.updateBook(updatedBook)
                            }

                        },
                        valueRange = 0f..300f
                    )
                }
                Column { Text(text = "Page Count: " + sliderValueDuration) }
            }
            Column {
                Column {
                    Slider(
                        value = sliderValueBookstamp.toFloat(),
                        onValueChange = {
                            sliderValueBookstamp = it.toInt()
                        },
                        onValueChangeFinished = {
                            if (updatedBook != null) {
                                updatedBook.bookstamp = sliderValueBookstamp
                                viewModel.updateBook(updatedBook)
                            }

                        },
                        valueRange = 0f..sliderValueDuration.toFloat()
                    )
                }
                Column { Text(text = "Bookstamp: " + sliderValueBookstamp) }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Favorite: ")
                var checkboxValue by remember {
                    mutableStateOf(bookEntity.favorite)
                }
                Column {
                    Checkbox(
                        checked = checkboxValue,
                        onCheckedChange = {
                            checkboxValue = it
                            if (updatedBook != null) {
                                updatedBook.favorite = checkboxValue
                                viewModel.updateBook(updatedBook)
                            }
                        }
                    )
                }
            }

            Row(Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically) {
                var state by remember { mutableStateOf(bookEntity.status) }
                Text(text = "Reading:")
                RadioButton(
                    selected = state == 0,
                    onClick = {
                        state = 0
                        if (updatedBook != null) {
                            updatedBook.status = state
                            viewModel.updateBook(updatedBook)
                        }
                    }
                )
                Text(text = "On Hold:")
                RadioButton(
                    selected = state == 1,
                    onClick = {
                        state = 1
                        if (updatedBook != null) {
                            updatedBook.status = state
                            viewModel.updateBook(updatedBook)
                        }
                    }
                )
                Text(text = "Finished:")
                RadioButton(
                    selected = state == 2,
                    onClick = {
                        state = 2
                        if (updatedBook != null) {
                            updatedBook.status = state
                            viewModel.updateBook(updatedBook)
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
                            viewModel.deleteBook()
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
