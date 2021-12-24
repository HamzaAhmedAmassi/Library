import com.h.alamassi.library.fragment.BooksFragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.h.alamassi.library.R
import com.h.alamassi.library.databinding.FragmentCreateBookBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.fragment.CategoriesFragment
import com.h.alamassi.library.model.Book

class CreateBookFragment : Fragment() {
    companion object {
        private const val TAG = "CreateBookFragment"
        const val IMAGE_REQUEST_CODE = 103
    }

    lateinit var databaseHelper: DatabaseHelper
    private lateinit var createBookBinding: FragmentCreateBookBinding
    private var imagePath: String = ""
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
        categoryId = arguments?.getLong("category_id") ?: -1
        if (categoryId == -1L) {
            return
        }



        createBookBinding.btnCreateBook.setOnClickListener {
            createBook()
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

    private fun createBook() {
        val bookName = createBookBinding.edName.text.toString()
        val bookAuthor = createBookBinding.edAuthor.text.toString()
        val bookYear = createBookBinding.edYear.text.toString()
        val bookCopies = createBookBinding.edCopies.text.toString()
        val bookImage = imagePath
        val bookPages = createBookBinding.edPages.text.toString()
        val bookDescription = createBookBinding.edDescription.text.toString()
        val bookLanguage = createBookBinding.edLanguage.text.toString()
        val bookShelf = createBookBinding.edShelf.text.toString()
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
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, BooksFragment()).commit()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            if (data.data != null) {
                val split: Array<String> =
                    data.data!!.path!!.split(":".toRegex()).toTypedArray() //split the path.
                val filePath = split[1] //assign it to a string(your choice).
                val bm = BitmapFactory.decodeFile(filePath)
                createBookBinding.ivBookImage.setImageBitmap(bm)

                imagePath = filePath
                Log.d(TAG, "onActivityResult: imagePath $imagePath")
            }
        }
    }

}
