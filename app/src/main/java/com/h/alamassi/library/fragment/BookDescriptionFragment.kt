package com.h.alamassi.library.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.h.alamassi.library.LoginActivity
import com.h.alamassi.library.R
import com.h.alamassi.library.databinding.FragmentBookDescriptionBinding
import com.h.alamassi.library.datasource.DatabaseHelper

class BookDescriptionFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var bookDescriptionBinding: FragmentBookDescriptionBinding
    private var imagePath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookDescriptionBinding = FragmentBookDescriptionBinding.inflate(inflater, container, false)
        return bookDescriptionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookId = arguments?.getLong("book_id") ?: -1
        databaseHelper = DatabaseHelper(requireContext())
        if (bookId == -1L) {
            val login = Intent(activity, CategoriesFragment::class.java)
            startActivity(login)
        } else {
            val books = databaseHelper.getDescriptionBooks(bookId)
            bookDescriptionBinding.tvYear.text = books.year
            bookDescriptionBinding.tvAuthor.text = books.author
            bookDescriptionBinding.tvBookName.text = books.name
            bookDescriptionBinding.tvDescription.text = books.description
            bookDescriptionBinding.tvLanguage.text = books.language
            bookDescriptionBinding.tvNuOfCopies.text = books.numOfCopies
            bookDescriptionBinding.tvNumOfPages.text = books.numOfPages
            bookDescriptionBinding.tvShelf.text = books.shelf
            val bm = BitmapFactory.decodeFile(books.image!!)
            bookDescriptionBinding.ivImageBook.setImageBitmap(bm)
            bookDescriptionBinding.ibEdit.setOnClickListener {
                val bundle = Bundle()
                bundle.putLong("book_id", bookId)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BookEditFragment::class.java, bundle).commit()
            }
        }

    }


}