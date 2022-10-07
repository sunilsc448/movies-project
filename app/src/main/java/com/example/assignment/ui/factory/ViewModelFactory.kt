package com.example.assignment.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.data.repository.MoviesRepository
import com.example.assignment.ui.viewModel.MoviesViewModel
import com.example.assignment.ui.viewModel.PlaylistViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: MoviesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(PlaylistViewModel::class.java)) {
            return PlaylistViewModel(repository) as T
        }
        throw IllegalArgumentException("This viewModel is not supported")
    }
}