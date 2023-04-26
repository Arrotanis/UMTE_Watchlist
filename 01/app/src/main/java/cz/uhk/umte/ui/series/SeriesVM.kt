package cz.uhk.umte.ui.series

import cz.uhk.umte.data.db.dao.SeriesDao
import cz.uhk.umte.data.db.entities.SeriesEntity
import cz.uhk.umte.ui.base.BaseViewModel
import java.util.Date

class SeriesVM(
    private val seriesDao: SeriesDao,
) : BaseViewModel() {
    var filter = "Watching"
    var status = 0
    var search = ""
    var series = seriesDao.selectAll(status,search)
    fun addSeries(text: String) {
        launch {
            seriesDao.insertOrUpdate(
                series = SeriesEntity(
                    name = text,
                    changeDate = Date()
                )
            )
        }
    }

    fun refresh() {
        series = seriesDao.selectAll(status,search)
    }
}