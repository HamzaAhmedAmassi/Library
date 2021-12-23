package com.h.alamassi.library.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.h.alamassi.library.R
import com.h.alamassi.library.R.drawable.ic_baseline_menu_book_24
import com.h.alamassi.library.databinding.FragmentBookDescriptionBinding
import com.h.alamassi.library.datasource.DatabaseHelper

class BookDescriptionFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var bookDescriptionBinding: FragmentBookDescriptionBinding
    var bookImage: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookDescriptionBinding = FragmentBookDescriptionBinding.inflate(inflater, container, false)
        return bookDescriptionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookImage = arguments?.getString("book_image")

        val bookId = arguments?.getLong("book_id") ?: -1
        if (bookId == -1L) {
            return
        } else {

            bookDescriptionBinding.ibEdit.setOnClickListener {
                val bundle = Bundle()
                bundle.putLong("book_id", bookId)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BookEditFragment::class.java, bundle).commit()
            }
        }

    }

    fun bookDescription() {
        val name = bookDescriptionBinding.tvBookName
        val author = bookDescriptionBinding.tvAuthor
        val year = bookDescriptionBinding.tvYear
        val language = bookDescriptionBinding.tvLanguage
        val nuOfCopies = bookDescriptionBinding.tvNuOfCopies
        val nuOfPages = bookDescriptionBinding.tvNumOfPages
        val description = bookDescriptionBinding.tvDescription
        val shelf = bookDescriptionBinding.tvShelf
        var image = bookDescriptionBinding.ivImageBook.toString()
        image = if (image == null) {
            ic_baseline_menu_book_24.toString()
        } else {
            bookImage as String
        }
    }
}