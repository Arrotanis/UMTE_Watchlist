package cz.uhk.umte.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var name: String = "",
    var description: String = "",
    var status: Int = 0,
    var favorite: Boolean = false,
    var changeDate: Date,
    var pages: Int = 0,
    var bookstamp: Int = 0,
    var author: String = "",
)