package com.example.assignment.di.component

import com.example.assignment.ui.view.fragment.PlaylistFragment
import com.example.assignment.di.module.ApiModule
import com.example.assignment.di.module.AppModule
import com.example.assignment.di.module.RoomModule
import com.example.assignment.di.scope.MainComponentScope
import com.example.assignment.ui.view.activity.MoviesActivity
import com.example.assignment.ui.view.fragment.MoviesListFragment
import dagger.Component

@Component(modules = [AppModule::class, ApiModule::class, RoomModule::class])
@MainComponentScope
interface MainComponent {
    fun injectActivity(activity: MoviesActivity)
    fun injectFragment(fragment: MoviesListFragment)
    fun injectFragment(fragment: PlaylistFragment)
}