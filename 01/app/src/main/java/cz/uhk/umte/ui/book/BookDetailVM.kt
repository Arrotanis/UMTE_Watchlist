package cz.uhk.umte.ui.book

import cz.uhk.umte.data.db.dao.BookDao
import cz.uhk.umte.data.db.entities.BookEntity
import cz.uhk.umte.ui.base.BaseViewModel
import kotlinx.coroutines.flow.first
import java.util.*

class BookDetailVM(
    bookId: Long,
    private val bookDao: BookDao
) : BaseViewModel() {
    val book = bookDao.selectById(bookId)

    fun deleteBook() {
        launch {
            bookDao.delete(book = book.first())
        }
    }

    fun updateBook(text: String) {
        launch {
            val updatedBook = book.first();
            updatedBook.name = text;
            updatedBook.changeDate = Date()
            bookDao.insertOrUpdate(book = updatedBook)
        }
    }

    fun updateBook(book: BookEntity) {
        launch {
            book.changeDate = Date()
            bookDao.insertOrUpdate(book = book)
        }
    }
}