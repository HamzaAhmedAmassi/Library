package com.h.alamassi.library.fragment

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.h.alamassi.library.R
import com.h.alamassi.library.adapter.BookAdapter
import com.h.alamassi.library.databinding.FragmentBookDescriptionBinding
import com.h.alamassi.library.databinding.FragmentBooksBinding
import com.h.alamassi.library.datasource.DatabaseHelper

class BookDescriptionFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var bookDescriptionBinding: FragmentBookDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookDescriptionBinding = FragmentBookDescriptionBinding.inflate(inflater, container, false)
        return bookDescriptionBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookId = arguments?.getLong("book_id")?:-1
        if (bookId == -1L){
            return
        }else{

            bookDescriptionBinding.ibEdit.setOnClickListener {
                val bundle = Bundle()
                bundle.putLong("book_id",bookId)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BookEditFragment::class.java,bundle).commit()
            }
        }

    }
}