package com.h.alamassi.library.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.h.alamassi.library.MainActivity
import com.h.alamassi.library.adapter.FavouritesAdapter
import com.h.alamassi.library.databinding.FragmentFavoriteBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.model.Book


class FavouritesFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var favoriteBinding: FragmentFavoriteBinding
companion object{
    val data = ArrayList<Book>()
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())
        val favourites = FavouritesAdapter(requireContext() as MainActivity, data)
        favoriteBinding.rvFavourites.layoutManager = LinearLayoutManager(requireContext())
        favoriteBinding.rvFavourites.layoutManager = LinearLayoutManager(requireContext())
        favoriteBinding.rvFavourites.adapter = favourites
    }

}