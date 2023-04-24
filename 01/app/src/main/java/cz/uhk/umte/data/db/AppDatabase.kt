package cz.uhk.umte.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cz.uhk.umte.data.db.dao.MovieDao
import cz.uhk.umte.data.db.entities.MovieEntity

@Database(
    version = AppDatabase.Version,
    entities = [
        MovieEntity::class
    ],
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val Version = 1
        const val Name = "UmteDatabase"
    }
}