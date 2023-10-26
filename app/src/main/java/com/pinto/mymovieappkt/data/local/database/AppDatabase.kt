package com.pinto.mymovieappkt.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pinto.mymovieappkt.data.local.dao.MovieDao
import com.pinto.mymovieappkt.data.local.dao.TvDao
import com.pinto.mymovieappkt.data.local.entity.FavoriteMovieEntity
import com.pinto.mymovieappkt.data.local.entity.FavoriteTvEntity

@Database(entities = [FavoriteMovieEntity::class, FavoriteTvEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao
}