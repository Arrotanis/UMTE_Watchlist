package cz.uhk.umte.ui.series

import cz.uhk.umte.data.db.dao.SeriesDao
import cz.uhk.umte.data.db.entities.SeriesEntity
import cz.uhk.umte.ui.base.BaseViewModel
import kotlinx.coroutines.flow.first
import java.util.*

class SeriesDetailVM(
    seriesId: Long,
    private val seriesDao: SeriesDao
) : BaseViewModel() {
    val series = seriesDao.selectById(seriesId)

    fun deleteSeries() {
        launch {
            seriesDao.delete(series = series.first())
        }
    }

    fun updateSeries(text: String) {
        launch {
            val updatedSeries = series.first();
            updatedSeries.name = text;
            updatedSeries.changeDate = Date()
            seriesDao.insertOrUpdate(series = updatedSeries)
        }
    }

    fun updateSeries(series: SeriesEntity) {
        launch {
            series.changeDate = Date()
            seriesDao.insertOrUpdate(series = series)
        }
    }
}