package com.h.alamassi.library.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.h.alamassi.library.MainActivity
import com.h.alamassi.library.R
import com.h.alamassi.library.adapter.CategoryAdapter
import com.h.alamassi.library.databinding.FragmentCategoryBinding
import com.h.alamassi.library.datasource.DatabaseHelper

class CategoriesFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var categoryBinding: FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        categoryBinding = FragmentCategoryBinding.inflate(inflater, container, false)
        return categoryBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())

        val data = databaseHelper.getAllCategories()
        val bookAdapter = CategoryAdapter(requireActivity() as MainActivity, data)

        categoryBinding.rvCategory.layoutManager = GridLayoutManager(requireActivity(), 3)
        categoryBinding.rvCategory.adapter = bookAdapter
        categoryBinding.root.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,BooksFragment()).commit()

        }

        categoryBinding.fabAddCategory.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment_container,CreateCategoryFragment()).commit()
        }
    }

}