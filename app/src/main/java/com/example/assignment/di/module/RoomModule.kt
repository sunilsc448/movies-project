package com.example.assignment.di.module

import android.app.Application
import androidx.room.Room
import com.example.assignment.data.api.DATABASE_NAME
import com.example.assignment.data.database.MoviesRoomDataBase
import com.example.assignment.di.scope.MainComponentScope
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @MainComponentScope
    @Provides
    fun getRoomDataBase(application: Application):MoviesRoomDataBase{
        return Room.databaseBuilder(application, MoviesRoomDataBase::class.java, DATABASE_NAME).build()
    }
}