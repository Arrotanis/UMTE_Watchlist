package cz.uhk.umte.ui.movie

import cz.uhk.umte.data.db.dao.MovieDao
import cz.uhk.umte.data.db.entities.MovieEntity
import cz.uhk.umte.ui.base.BaseViewModel
import java.util.Date

class MovieVM(
    private val movieDao: MovieDao,
) : BaseViewModel() {
    var filter = "Watching"
    var status = 0
    var search = ""
    var movies = movieDao.selectAll(status,search)
    fun addMovie(text: String) {
        launch {
            movieDao.insertOrUpdate(
                movie = MovieEntity(
                    name = text,
                    changeDate = Date()
                )
            )
        }
    }

    fun refresh() {
        movies = movieDao.selectAll(status,search)
    }
}