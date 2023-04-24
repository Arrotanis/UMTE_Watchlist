package cz.uhk.umte.ui.movie

import cz.uhk.umte.data.db.dao.MovieDao
import cz.uhk.umte.data.db.entities.MovieEntity
import cz.uhk.umte.ui.base.BaseViewModel
import java.util.Date

class MovieVM(
    private val movieDao: MovieDao,
    ) : BaseViewModel() {
    var movies = movieDao.selectAll(0)
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
    fun changeStatus(int: Int) {
        movies = movieDao.selectAll(int)
    }
}