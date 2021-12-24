package com.h.alamassi.library.fragment

import CreateBookFragment
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.h.alamassi.library.model.User

class ProfileEditFragment : Fragment() {
    companion object {
        const val IMAGE_REQUEST_CODE = 101
    }

    private var imageURI: String = ""
    lateinit var databaseHelper: DatabaseHelper
    lateinit var profileEditBinding: FragmentProfileEditBinding
    val currentUserId = 1
    //       SharedPreferenceHelper.getInstance(requireContext())?.getInt("currentUserId", -1)

//    val userImage = requireArguments().getString("user_image")
//    val userName = requireArguments().getString("user_name")
//    val userPassword = requireArguments().getString("user_password")


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

                profileEditBinding.btnSaveEditProfile.setOnClickListener {
                    updateProfile()
                }
                profileEditBinding.fabChooseImage.setOnClickListener {
                    chooseImage()
                }

            }

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

    private fun updateProfile() {
        if (currentUserId == -1) {
            return
        } else {
            val userId = arguments?.getLong("user_id") ?: -1
            if (userId == -1L) {
                Toast.makeText(requireContext(), "Cannot Edit", Toast.LENGTH_SHORT).show()
                return
            } else {
                val user = databaseHelper.getUser(userId)
                user!!.name = profileEditBinding.etName.toString()
                user.password = profileEditBinding.etPassword.toString()
                user.image = profileEditBinding.imageView.toString()
                val name = user.name
                val password = user.password
                val image = user.image
                databaseHelper.updateUser(
                    User(
                        name,
                        password, image
                    )
                )
                Toast.makeText(requireContext(), "Edit Successfully", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK && data != null) {

            profileEditBinding.imageView.setImageURI(data.data)
            imageURI = data.data.toString()
        }
    }

}