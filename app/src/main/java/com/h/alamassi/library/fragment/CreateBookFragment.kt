package com.h.alamassi.library.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.h.alamassi.library.databinding.FragmentCreateBookBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.model.Book

class CreateBookFragment : Fragment() {
    companion object {
        const val IMAGE_REQUEST_CODE = 101
    }

    lateinit var databaseHelper: DatabaseHelper
    private lateinit var createBookBinding: FragmentCreateBookBinding
    private var imageURI: String = ""
    var categoryId: Long = -1L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        createBookBinding = FragmentCreateBookBinding.inflate(inflater, container, false)
        return createBookBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())


        categoryId = arguments?.getLong("category_id")?:-1
        if (categoryId == -1L){


            return
        }

        createBookBinding.btnSaveBook.setOnClickListener {
            createCategory()
        }
        createBookBinding.fabChooseImage.setOnClickListener {
            chooseImage()
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
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                IMAGE_REQUEST_CODE
            )
        }
    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            IMAGE_REQUEST_CODE
        )
    }

    private fun createCategory() {
        val bookName = createBookBinding.txtName.text.toString()
        val bookAuthor = createBookBinding.txtAuthor.text.toString()
        val bookYear = createBookBinding.txtYear.text.toString()
        val bookCopies = createBookBinding.txtCopies.text.toString()
        val bookImage = imageURI
        val bookPages = createBookBinding.txtPages.text.toString()
        val bookDescription = createBookBinding.txtDescription.text.toString()
        val bookLanguage = createBookBinding.txtLanguage.text.toString()
        val bookShelf = createBookBinding.txtShelf.text.toString()
        if (bookName.isEmpty() && bookAuthor.isEmpty() && bookDescription.isEmpty() && bookLanguage.isEmpty() && bookShelf.isEmpty()) {
            Toast.makeText(requireContext(), "Invalid data", Toast.LENGTH_SHORT).show()
        } else {


            val book = Book(
                bookName,
                bookAuthor,
                bookYear,
                categoryId,
                bookDescription,
                bookLanguage,
                bookCopies,
                bookPages,
                bookShelf,
                bookImage
            )

            val result = databaseHelper.insertBook(book)
            if (result != -1L) {
                Toast.makeText(requireContext(), "Book Created Successfully", Toast.LENGTH_SHORT)
                    .show()

            } else {
                Toast.makeText(
                    requireContext(),
                    "Something error, Check data again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}