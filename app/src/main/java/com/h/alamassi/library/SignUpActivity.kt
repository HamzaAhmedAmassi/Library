package com.h.alamassi.library

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
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
        private const val TAG = "SignUpActivity"
        const val IMAGE_REQUEST_CODE = 101
    }

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sinUpBinding: ActivitySignUpBinding
    private var imagePath: String = ""

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
            IMAGE_REQUEST_CODE
        )
    }


    private fun onClickRegister() {
        val username = sinUpBinding.txtUserName.text.toString()
        val password = sinUpBinding.txtPassword.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty()) {
            var image = imageURI
            if (image == null){
                image = R.drawable.ic_baseline_person_24.toString()
            }


//            fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//
//            }
            /*val user = databaseHelper.authUser(username, password)
        if (user == null) {*/
            //Create New User
            val newUser = User(username, password, imagePath)
            val result = databaseHelper.createUser(newUser)
            if (result != -1L) {
                Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("user_image",image)
                bundle.putString("user_name",username)
                bundle.putString("user_password",password)
                //Move to MainActivity
                startActivity(Intent(this, MainActivity::class.java),bundle)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            if (data.data != null) {
                val split: Array<String> = data.data!!.path!!.split(":".toRegex()).toTypedArray() //split the path.
                val filePath = split[1] //assign it to a string(your choice).
                val bm = BitmapFactory.decodeFile(filePath)
                sinUpBinding.ivUserPhoto.setImageBitmap(bm)

                imagePath = filePath
                Log.d(TAG, "onActivityResult: imagePath $imagePath")
            }
        }
    }

}

