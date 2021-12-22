package com.h.alamassi.library

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.h.alamassi.library.databinding.ActivitySignUpBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.datasource.SharedPreferenceHelper
import com.h.alamassi.library.fragment.CreateCategoryFragment
import com.h.alamassi.library.model.User

class SignUpActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_REQUEST_CODE = 101
    }

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sinUpBinding: ActivitySignUpBinding
    private var imageURI: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sinUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(sinUpBinding.root)
        databaseHelper = DatabaseHelper(this)


        sinUpBinding.btnReg.setOnClickListener {
            onClickRegister()
        }
        sinUpBinding.fabChooseImage.setOnClickListener {
            chooseImage()
        }
    }

    private fun chooseImage() {
        val galleryPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (galleryPermission != PackageManager.PERMISSION_DENIED) {
            //Open PickImageActivity
            chooseImageFromGallery()
        } else {
            //Ask User
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                CreateCategoryFragment.IMAGE_REQUEST_CODE
            )
        }
    }

    private fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            CreateCategoryFragment.IMAGE_REQUEST_CODE
        )
    }


    private fun onClickRegister() {
        val username = sinUpBinding.txtUserName.text.toString()
        val password = sinUpBinding.txtPassword.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty()) {
//            val image = sinUpBinding.ivUserPhoto.setImageURI()
//            imageURI = image.toString()


//            fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//
//            }
            /*val user = databaseHelper.authUser(username, password)
        if (user == null) {*/
            //Create New User
            val newUser = User(username, password, "")
            val result = databaseHelper.createUser(newUser)
            if (result != -1L) {
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                //Move to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                //Change isLogin in SP to true
                SharedPreferenceHelper.getInstance(this)
                    ?.setInt("currentUserId", result.toInt())
                SharedPreferenceHelper.getInstance(this)?.setBoolean("isLogin", true)
            } else {
                Toast.makeText(
                    this,
                    "Something error, Please try again later",
                    Toast.LENGTH_SHORT
                ).show()
            }
            /*} else {
            Toast.makeText(this, "Name is token", Toast.LENGTH_SHORT).show()
        }*/
        } else {
            Toast.makeText(this, "Must fill", Toast.LENGTH_SHORT).show()
        }
    }

}

