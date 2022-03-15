package com.example.bioMetricApp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bioMetricApp.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_btn.setOnClickListener {
            checkAuthentication()
        }

    }

    private fun checkAuthentication() {
        val username = username_tv.text.toString()
        val password = password_tv.text.toString()
        if (username == "user" && password == "123") {
            navigateToActivity()
        } else {
            Toast.makeText(this, "Unable to login use Biometric", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}