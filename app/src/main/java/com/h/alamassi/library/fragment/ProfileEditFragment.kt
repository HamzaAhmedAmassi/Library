package com.h.alamassi.library.fragment

import android.Manifest
import android.app.Activity
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
import com.h.alamassi.library.LoginActivity
import com.h.alamassi.library.databinding.FragmentProfileEditBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.datasource.SharedPreferenceHelper
import com.h.alamassi.library.model.User

class ProfileEditFragment : Fragment() {
    companion object {
        const val IMAGE_REQUEST_CODE = 101
        private const val TAG = "ProfileEditFragment"
    }

    private var imagePath: String = ""
    lateinit var databaseHelper: DatabaseHelper
    lateinit var profileEditBinding: FragmentProfileEditBinding
    var currentUserId = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileEditBinding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return profileEditBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseHelper = DatabaseHelper(requireContext())
        currentUserId =
            SharedPreferenceHelper.getInstance(requireContext())!!.getInt("currentUserId", -1) ?: -1
        if (currentUserId == -1) {
            // TODO: 12/14/2021 Logout because no session expired
            val login = Intent(activity, LoginActivity::class.java)
            startActivity(login)
        } else {
            val currentUser = databaseHelper.getUser(currentUserId.toLong())
            if (currentUser == null) {
                // TODO: 12/14/2021 Logout because no user id found
                val login = Intent(activity, LoginActivity::class.java)
                startActivity(login)
            } else {

                //init current user data

                profileEditBinding.btnSaveEditProfile.setOnClickListener {
                    updateProfile()
                }
                profileEditBinding.fabChooseImage.setOnClickListener {
                    chooseImage()
                }
            }

        }
    }

    private fun updateProfile() {


        val name = profileEditBinding.etName.toString()
        val password = profileEditBinding.etPassword.toString()
        val image = profileEditBinding.ivUser.toString()

        val user = User(name, password, image)
        user.id = currentUserId

        databaseHelper.updateUser(user)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            if (data.data != null) {
                val split: Array<String> =
                    data.data!!.path!!.split(":".toRegex()).toTypedArray() //split the path.
                val filePath = split[1] //assign it to a string(your choice).
                val bm = BitmapFactory.decodeFile(filePath)
                profileEditBinding.ivUser.setImageBitmap(bm)

                imagePath = filePath
                Log.d(TAG, "onActivityResult: imagePath $imagePath")
            }
        }
    }

}