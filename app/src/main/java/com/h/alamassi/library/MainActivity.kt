package com.h.alamassi.library

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.h.alamassi.library.databinding.ActivityMainBinding
import com.h.alamassi.library.datasource.SharedPreferenceHelper
import com.h.alamassi.library.fragment.CategoriesFragment
import com.h.alamassi.library.fragment.FavouritesFragment
import com.h.alamassi.library.fragment.ProfileShowFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CategoriesFragment())
            .commit()

        binding.bottomNavigation.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.nav_home -> CategoriesFragment()
                R.id.nav_profile -> ProfileShowFragment()
                R.id.nav_favorite -> FavouritesFragment()
                else -> CategoriesFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logOutItem -> onClickLogout()
            //R.id.searchItem -> onClickLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onClickLogout() {

        SharedPreferenceHelper.getInstance(this)?.setInt("currentUserId", -1)
        SharedPreferenceHelper.getInstance(this)?.setBoolean("isLogin", false)
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
        finish()
    }
}

//    private fun onClickCreateCategory() {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, CreateCategoryFragment()).commit()
//    }
//
//    private fun onClickProfile() {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, ProfileEditFragment()).commit()
//    }






