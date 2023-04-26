package cz.uhk.umte.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.uhk.umte.data.db.dao.BookDao
import cz.uhk.umte.data.db.dao.MovieDao
import cz.uhk.umte.data.db.dao.SeriesDao
import cz.uhk.umte.data.db.entities.BookEntity
import cz.uhk.umte.data.db.entities.MovieEntity
import cz.uhk.umte.data.db.entities.SeriesEntity

@Database(
    version = AppDatabase.Version,
    entities = [
        MovieEntity::class,
        BookEntity::class,
        SeriesEntity::class
    ],
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
    abstract fun bookDao(): BookDao

    companion object {
        const val Version = 1
        const val Name = "UmteDatabase"
    }
}