package ar.edu.unicen.filmzoom.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ar.edu.unicen.filmzoom.data.local.dao.MovieDao
import ar.edu.unicen.filmzoom.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class FilmZoomDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "filmzoom_db"
    }
}