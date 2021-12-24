package com.h.alamassi.library.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private var imagePath: String? = null
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

        val bookId = arguments?.getLong("book_id") ?: -1
        databaseHelper = DatabaseHelper(requireContext())
        val books = databaseHelper.getDescriptionBooks(bookId)

        val name = bookEditBinding.edName.text.toString()
        val author = bookEditBinding.edAuthor.text.toString()
        val year = bookEditBinding.edYear.text.toString()
        val description = bookEditBinding.edDescription.text.toString()
        val language = bookEditBinding.edLanguage.text.toString()
        val pages = bookEditBinding.edPages.text.toString()
        val copies = bookEditBinding.edCopies.text.toString()
        val shelf = bookEditBinding.edShelf.text.toString()
        val image = bookEditBinding.ivBookImage.toString()
        val book = Book(
            name,
            author,
            year,
            categoryId,
            description,
            language,
            pages,
            copies,
            shelf,
            image
        )
        book.id = bookId
        databaseHelper.updateBook(book)
        Toast.makeText(requireContext(), "Edit Successfully", Toast.LENGTH_SHORT).show()

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
                ProfileEditFragment.IMAGE_REQUEST_CODE
            )
        }
    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            ProfileEditFragment.IMAGE_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ProfileEditFragment.IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            if (data.data != null) {
                val split: Array<String> =
                    data.data!!.path!!.split(":".toRegex()).toTypedArray() //split the path.
                val filePath = split[1] //assign it to a string(your choice).
                val bm = BitmapFactory.decodeFile(filePath)
                bookEditBinding.ivBookImage.setImageBitmap(bm)

                imagePath = filePath
            }
        }
    }

}