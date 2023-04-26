package cz.uhk.umte.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var name: String = "",
    var description: String = "",
    var status: Int = 0,
    var favorite: Boolean = false,
    var changeDate: Date,
    var duration: Int = 180,
    var timestamp: Int = 0,
)
