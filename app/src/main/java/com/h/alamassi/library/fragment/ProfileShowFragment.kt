package com.h.alamassi.library.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.h.alamassi.library.LoginActivity
import com.h.alamassi.library.R
import com.h.alamassi.library.databinding.FragmentProfileShowBinding
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.datasource.SharedPreferenceHelper


class ProfileShowFragment : Fragment() {

    lateinit var databaseHelper: DatabaseHelper
    lateinit var profileShowFragment: FragmentProfileShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileShowFragment = FragmentProfileShowBinding.inflate(inflater)
        return profileShowFragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseHelper = DatabaseHelper(requireContext())

        val userId = SharedPreferenceHelper.getInstance(requireContext())?.getInt("currentUserId",-1)
        if (userId == -1) {
            val login = Intent(activity, LoginActivity::class.java)
            startActivity(login)
        } else {
            val user = databaseHelper.getUser(userId!!.toLong())
            profileShowFragment.etName.text = user?.name
            profileShowFragment.etPassword.text = user?.password
            val bm = BitmapFactory.decodeFile(user!!.image!!)
            profileShowFragment.ivUser.setImageBitmap(bm)
        }
        profileShowFragment.ibEdit.setOnClickListener {
            val bundle = Bundle()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileEditFragment::class.java, bundle).commit()

        }
    }

}