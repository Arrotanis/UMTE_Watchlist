package cz.uhk.umte.di

import androidx.room.Room
import cz.uhk.umte.data.db.AppDatabase
import cz.uhk.umte.ui.book.BookDetailVM
import cz.uhk.umte.ui.book.BookVM
import cz.uhk.umte.ui.movie.MovieDetailVM
import cz.uhk.umte.ui.movie.MovieVM
import cz.uhk.umte.ui.series.SeriesDetailVM
import cz.uhk.umte.ui.series.SeriesVM
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModules by lazy { listOf(dataModule, uiModule) }

val dataModule = module {
    db()
}

val uiModule = module {
    viewModel { (movieId: Long) -> MovieDetailVM(movieId, get()) }
    viewModel { MovieVM(get()) }
    viewModel { (bookId: Long) -> BookDetailVM(bookId, get()) }
    viewModel { BookVM(get()) }
    viewModel { (seriesId: Long) -> SeriesDetailVM(seriesId, get()) }
    viewModel { SeriesVM(get()) }
}

private fun Module.db() {
    // DB
    single {
        Room
            .databaseBuilder(
                context = androidApplication(),
                klass = AppDatabase::class.java,
                name = AppDatabase.Name,
            )
            .build()
    }
    // Dao
    single { get<AppDatabase>().movieDao() }
    single { get<AppDatabase>().bookDao() }
    single { get<AppDatabase>().seriesDao() }
}