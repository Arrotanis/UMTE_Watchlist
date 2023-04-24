package cz.uhk.umte.ui.movie

import cz.uhk.umte.data.db.dao.MovieDao
import cz.uhk.umte.data.db.entities.MovieEntity
import cz.uhk.umte.ui.base.BaseViewModel
import kotlinx.coroutines.flow.first
import java.util.*

class MovieDetailVM(
    private val movieId: Long,
    private val movieDao: MovieDao
) : BaseViewModel() {
    val movie = movieDao.selectById(movieId)

    fun deleteMovie() {
        launch {
            movieDao.delete(movie = movie.first())
        }
    }

    fun updateMovie(text: String) {
        launch {
            val updatedMovie = movie.first();
            updatedMovie.name=text;
            updatedMovie.changeDate= Date()
            movieDao.insertOrUpdate(movie=updatedMovie)
        }
    }
    fun updateMovie(movie: MovieEntity) {
        launch {
            movie.changeDate=Date()
            movieDao.insertOrUpdate(movie=movie)
        }
    }
}