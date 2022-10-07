package com.example.assignment.di.module

import android.app.Application
import com.example.assignment.di.scope.MainComponentScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {
    @MainComponentScope
    @Provides
    fun getAppContext():Application = application
}