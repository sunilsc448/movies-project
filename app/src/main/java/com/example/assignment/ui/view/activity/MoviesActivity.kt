package com.example.assignment.ui.view.activity

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.assignment.BR
import com.example.assignment.R
import com.example.assignment.databinding.ActivityMoviesBinding
import com.example.assignment.ui.AppClass
import com.example.assignment.ui.factory.ViewModelFactory
import com.example.assignment.ui.view.fragment.MoviesListFragmentDirections
import com.example.assignment.ui.view.fragment.PlaylistFragmentDirections
import com.example.assignment.ui.viewModel.MoviesViewModel
import javax.inject.Inject

class MoviesActivity : AppCompatActivity() {
    private lateinit var dataBinding:ActivityMoviesBinding
    private lateinit var viewModel: MoviesViewModel
    @Inject lateinit var viewModelFactory:ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies)
        AppClass.application.getMainComponent().injectActivity(this)
        setViewModel()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getFilterClicked().observe(this) {
            findNavController(R.id.navHostFragment).navigate(MoviesListFragmentDirections.playlistClicked())
            viewModel.dropSelectedMovie()
        }
        viewModel.getAllPLayListClicked().observe(this) {
            findNavController(R.id.navHostFragment).navigate(PlaylistFragmentDirections.playListBackClicked())
            viewModel.dropSelectedMovie()
        }
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)
        dataBinding.setVariable(BR.viewModel, viewModel)
    }
}