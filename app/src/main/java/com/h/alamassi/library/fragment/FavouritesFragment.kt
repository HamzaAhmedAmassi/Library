package com.h.alamassi.library.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.h.alamassi.library.R
import com.h.alamassi.library.databinding.FragmentFavoriteBinding
import com.h.alamassi.library.datasource.DatabaseHelper


class FavouritesFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var favoriteBinding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}