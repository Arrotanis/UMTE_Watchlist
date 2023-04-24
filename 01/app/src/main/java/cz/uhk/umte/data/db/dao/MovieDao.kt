package cz.uhk.umte.data.db.dao

import androidx.room.*
import cz.uhk.umte.data.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("Select * From MovieEntity Order By favorite DESC, changeDate DESC")
    fun selectAll(): Flow<List<MovieEntity>>

    @Query("Select * From MovieEntity WHERE status = :status Order By favorite DESC, changeDate DESC")
    fun selectAll(status: Int): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("Select * From MovieEntity WHERE id = :id")
    fun selectById(id: Long): Flow<MovieEntity>
}