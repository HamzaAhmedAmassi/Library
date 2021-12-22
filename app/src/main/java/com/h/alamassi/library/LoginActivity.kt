package com.h.alamassi.library

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.h.alamassi.library.datasource.DatabaseHelper
import com.h.alamassi.library.databinding.ActivityLoginBinding
import com.h.alamassi.library.datasource.SharedPreferenceHelper


class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        databaseHelper = DatabaseHelper(this)

        //Check if isLogin true ==> Go to Home else do nothing
        val isLogin = SharedPreferenceHelper.getInstance(this)?.getBoolean("isLogin", false);
        if (isLogin == true) {
            val i = Intent(this, MainActivity::class.java)
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            startActivity(i)
            finish()
        }
        loginBinding.btnLogin.setOnClickListener {
            onClickLogin()
        }

        loginBinding.tvCreateAccount.setOnClickListener {
            onClickCreateAccount()
        }
    }

    private fun onClickLogin() {
        val username = loginBinding.edUserName.text.toString()
        val password = loginBinding.edPassword.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty()) {
            // TODO: 12/14/2021 Check if user exist in DB
            val user = databaseHelper.authUser(username, password)
            if (user != null) {
                SharedPreferenceHelper.getInstance(this)?.setInt("currentUserId", user.id ?: -1)
                SharedPreferenceHelper.getInstance(this)?.setBoolean("isLogin", true)
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid data, try again", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Must fill all information", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickCreateAccount() {
        val toSignUpActivity = Intent(this, SignUpActivity::class.java)
        startActivity(toSignUpActivity)
    }

}
