package cz.uhk.umte.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SeriesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    var name: String = "",
    var description: String = "",
    var status: Int = 0,
    var favorite: Boolean = false,
    var changeDate: Date,
    var duration: Int = 90,
    var timestamp: Int = 0,
    var episode: Int = 1,
    var season: Int = 1,
)