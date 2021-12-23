package com.h.alamassi.library.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.h.alamassi.library.R
import com.h.alamassi.library.databinding.FragmentBookEditBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.model.Book


class BookEditFragment : Fragment() {
    companion object {
        const val IMAGE_REQUEST_CODE = 101
    }

    lateinit var bookEditBinding: FragmentBookEditBinding
    lateinit var databaseHelper: DatabaseHelper
    var bookId: Long = -1L
    var categoryId: Long = -1L
    private var imageURI: String? = null
    var bookImage: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookEditBinding = FragmentBookEditBinding.inflate(inflater)
        return bookEditBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookId = arguments?.getLong("book_id", -1)!!
        categoryId = arguments?.getLong("category_id", -1)!!
        bookImage = arguments?.getString(
            "book_image",
            R.drawable.ic_baseline_menu_book_24.toString()
        )
        bookEditBinding.btnSaveBook.setOnClickListener {
            updateBooks()
        }
        bookEditBinding.fabChooseImage.setOnClickListener {
            chooseImage()
        }

    }


    private fun updateBooks() {
        if (bookId == -1L) {
            return
        } else {
            val id = bookId
            val name = bookEditBinding.txtName.toString()
            val author = bookEditBinding.txtAuthor.toString()
            val year = bookEditBinding.txtYear.toString()
            val categoryId = categoryId
            val description = bookEditBinding.txtDescription.toString()
            val language = bookEditBinding.txtLanguage.toString()
            val pages = bookEditBinding.txtPages.toString()
            val copies = bookEditBinding.txtCopies.toString()
            val shelf = bookEditBinding.txtShelf.toString()

            var image: String = bookEditBinding.ivBookImage.toString()
            if (image == null) {
                image = bookImage!!
            } else {
                image = imageURI!!
            }
            databaseHelper.updateBook(
                Book(
                    name,
                    author,
                    year,
                    categoryId,
                    description,
                    language,
                    pages,
                    copies,
                    shelf, image
                )
            )
        }
    }

    private fun chooseImage() {
        val galleryPermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (galleryPermission != PackageManager.PERMISSION_DENIED) {
            //Open PickImageActivity
            chooseImageFromGallery()
        } else {
            //Ask User
            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                CreateBookFragment.IMAGE_REQUEST_CODE
            )
        }
    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            CreateBookFragment.IMAGE_REQUEST_CODE
        )
    }

}