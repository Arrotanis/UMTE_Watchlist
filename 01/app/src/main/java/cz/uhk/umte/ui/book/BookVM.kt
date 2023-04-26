package cz.uhk.umte.ui.book

import cz.uhk.umte.data.db.dao.BookDao
import cz.uhk.umte.data.db.entities.BookEntity
import cz.uhk.umte.ui.base.BaseViewModel
import java.util.Date

class BookVM(
    private val bookDao: BookDao,
) : BaseViewModel() {
    var filter = "Reading"
    var status = 0
    var search = ""
    var books = bookDao.selectAll(status,search)
    fun addBook(text: String) {
        launch {
            bookDao.insertOrUpdate(
                book = BookEntity(
                    name = text,
                    changeDate = Date()
                )
            )
        }
    }

    fun refresh() {
        books = bookDao.selectAll(status,search)
    }
}