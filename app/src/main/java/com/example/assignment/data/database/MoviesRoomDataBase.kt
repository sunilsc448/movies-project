package com.example.assignment.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment.data.database.dao.MoviesDao
import com.example.assignment.data.database.dao.PlaylistAndMoviesDao
import com.example.assignment.data.database.dao.PlaylistsDao
import com.example.assignment.data.model.entity.Movie
import com.example.assignment.data.model.entity.Playlist
import com.example.assignment.data.model.entity.PlaylistAndMovie

private const val MOVIES_DATABASE_VERSION = 1

@Database(version = MOVIES_DATABASE_VERSION, entities = [Movie::class, Playlist::class, PlaylistAndMovie::class])
abstract class MoviesRoomDataBase:RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
    abstract fun playlistsDao(): PlaylistsDao
    abstract fun playlistAndMoviesDao():PlaylistAndMoviesDao
}