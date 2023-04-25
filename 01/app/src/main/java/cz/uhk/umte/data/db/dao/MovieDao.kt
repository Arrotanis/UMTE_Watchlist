package cz.uhk.umte.data.db.dao

import androidx.room.*
import cz.uhk.umte.data.db.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("Select * From MovieEntity WHERE status = :status AND name LIKE '%' || :search || '%' Order By favorite DESC, changeDate DESC")
    fun selectAll(status: Int, search: String?): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("Select * From MovieEntity WHERE id = :id")
    fun selectById(id: Long): Flow<MovieEntity>
}