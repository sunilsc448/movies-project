package com.example.assignment.ui.view.fragment

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.R
import com.example.assignment.databinding.FragmentPlaylistBinding
import com.example.assignment.ui.AppClass
import com.example.assignment.ui.factory.ViewModelFactory
import com.example.assignment.ui.viewModel.MoviesViewModel
import com.example.assignment.ui.viewModel.PlaylistViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class PlaylistFragment : BottomSheetDialogFragment() {
    private lateinit var moviesViewModel:MoviesViewModel
    private lateinit var viewModel: PlaylistViewModel
    private lateinit var dataBinding: FragmentPlaylistBinding
    @Inject lateinit var viewModelFactory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = FragmentPlaylistBinding.inflate(inflater, container, false)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        AppClass.application.getMainComponent().injectFragment(this)
        activity?.let {
            moviesViewModel = ViewModelProvider(activity!!, viewModelFactory).get(MoviesViewModel::class.java)
            dataBinding.moviesViewModel = moviesViewModel
        }
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
        dataBinding.playlistViewModel = viewModel

        initObservers()

        return dataBinding.root
    }

    private fun initObservers() {
        viewModel.getAddPLayListClicked().observe(this) {
            showAddPlayListDialog()
        }
    }

    private fun showAddPlayListDialog() {
        activity?.let {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(activity)
            val input = EditText(activity)
            input.setHint(resources.getString(R.string.create_playlist_dialog_title))
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton("OK") { _, _ ->
                viewModel.addItemToPlaylist(input.text.toString())
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }
    }
}