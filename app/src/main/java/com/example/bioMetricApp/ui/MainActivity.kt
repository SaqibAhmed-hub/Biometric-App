package com.example.bioMetricApp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.example.bioMetricApp.R
import com.example.bioMetricApp.common.BiometricAuthListener
import com.example.bioMetricApp.utils.BiometricUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BiometricAuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showBiometricOption()
        biometric_login_btn.setOnClickListener {
            BiometricUtils.showBiometericPrompt(
                activity = this,
                listener = this,
                cryptoObject = null,
                allowDeviceCredential = true
            )
        }
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

    private fun showBiometricOption() {
        login_btn.visibility =
            if (BiometricUtils.isBiometricReady(this)) View.VISIBLE else View.GONE
    }

    override fun onBiometricAuthenticateError(error: Int, errMsg: String) {
        Toast.makeText(this, "Biometric Access Denied!", Toast.LENGTH_SHORT).show()
    }

    override fun onBiometericAuthenticateSuccess(result: BiometricPrompt.AuthenticationResult) {
        navigateToActivity()
    }

    private fun navigateToActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
    }
}