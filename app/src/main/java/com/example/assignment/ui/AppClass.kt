@file:Suppress("DEPRECATION")

package com.example.assignment.ui

import android.app.Application
import com.example.assignment.di.component.DaggerMainComponent
import com.example.assignment.di.component.MainComponent
import com.example.assignment.di.module.ApiModule
import com.example.assignment.di.module.AppModule
import com.example.assignment.di.module.RoomModule

class AppClass:Application() {
    private lateinit var mainComponent: MainComponent
    override fun onCreate() {
        super.onCreate()
        application = this
        mainComponent = initMainComponent(this)
    }

    private fun initMainComponent(appClass: AppClass): MainComponent {
        return DaggerMainComponent.builder().
        appModule(AppModule(application)).
        apiModule(ApiModule()).
        roomModule(RoomModule()).
        build()
    }

    fun getMainComponent():MainComponent = mainComponent


    companion object{
        lateinit var application:AppClass
            private set
    }

}