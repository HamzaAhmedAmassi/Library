package com.h.alamassi.library.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.h.alamassi.library.R
import com.h.alamassi.library.adapter.BookAdapter
import com.h.alamassi.library.databinding.FragmentBooksBinding
import com.h.alamassi.library.datasource.DatabaseHelper

class BooksFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var booksBinding: FragmentBooksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        booksBinding = FragmentBooksBinding.inflate(inflater, container, false)
        return booksBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         val categoryId = arguments?.getLong("category_id") ?: -1

        if (categoryId == -1L) {

        } else {
            val books = databaseHelper.getCategoryBooks(categoryId)
            val bookAdapter = BookAdapter(requireActivity(), books)

            booksBinding.rvBook.layoutManager = LinearLayoutManager(requireActivity())
            booksBinding.rvBook.adapter = bookAdapter

            val bookId = arguments?.getLong("book_id") ?: -1
            if (bookId == -1L) {
                return
            } else {
                booksBinding.root.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putLong("book_id",bookId)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, BookDescriptionFragment::class.java,bundle).commit()
                }
            }

            booksBinding.fabAddBook.setOnClickListener {
                val bundle = Bundle()
                bundle.putLong("category_id", categoryId)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, CreateBookFragment::class.java, bundle)
                    .commit()
            }
        }


    }
}

