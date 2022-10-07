package com.example.assignment.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.databinding.FragmentMoviesListBinding
import com.example.assignment.ui.AppClass.Companion.application
import com.example.assignment.ui.factory.ViewModelFactory
import com.example.assignment.ui.viewModel.MoviesViewModel
import javax.inject.Inject

class MoviesListFragment : Fragment() {
    private lateinit var parentViewModel: MoviesViewModel
    private lateinit var dataBinding: FragmentMoviesListBinding
    @Inject lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application.getMainComponent().injectFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentMoviesListBinding.inflate(inflater, container, false)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        activity?.let {
            parentViewModel = ViewModelProvider(activity!!, viewModelFactory).get(MoviesViewModel::class.java)
            dataBinding.viewModel = parentViewModel
        }
        return dataBinding.root
    }
}