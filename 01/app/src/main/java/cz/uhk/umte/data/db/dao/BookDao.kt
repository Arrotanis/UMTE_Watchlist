package cz.uhk.umte.data.db.dao

import androidx.room.*
import cz.uhk.umte.data.db.entities.BookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("Select * From BookEntity WHERE status = :status AND name LIKE '%' || :search || '%' Order By favorite DESC, changeDate DESC")
    fun selectAll(status: Int, search: String?): Flow<List<BookEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(book: BookEntity)

    @Delete
    fun delete(book: BookEntity)

    @Query("Select * From BookEntity WHERE id = :id")
    fun selectById(id: Long): Flow<BookEntity>
}