package cz.uhk.umte.data.db.dao

import androidx.room.*
import cz.uhk.umte.data.db.entities.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    @Query("Select * From SeriesEntity WHERE status = :status AND name LIKE '%' || :search || '%' Order By favorite DESC, changeDate DESC")
    fun selectAll(status: Int, search: String?): Flow<List<SeriesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(series: SeriesEntity)

    @Delete
    fun delete(series: SeriesEntity)

    @Query("Select * From SeriesEntity WHERE id = :id")
    fun selectById(id: Long): Flow<SeriesEntity>
}