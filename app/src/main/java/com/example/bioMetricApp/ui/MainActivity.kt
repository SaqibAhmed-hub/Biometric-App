package com.example.bioMetricApp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.example.bioMetricApp.R
import com.example.bioMetricApp.common.BiometricAuthListener
import com.example.bioMetricApp.utils.BiometricUtils


class MainActivity : AppCompatActivity(), BiometricAuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BiometricUtils.isBiometricReady(this)) {
            BiometricUtils.showBiometricPrompt(
                activity = this,
                listener = this,
                cryptoObject = null,
            )
        } else {
            Toast.makeText(this, "No biometric feature perform on this device", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onBiometricAuthenticateError(error: Int, errMsg: String) {
        when (error) {
            BiometricPrompt.ERROR_USER_CANCELED -> finish()
            BiometricPrompt.ERROR_NEGATIVE_BUTTON -> {
                Toast.makeText(this, "Negative Button", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onBiometricAuthenticateSuccess(result: BiometricPrompt.AuthenticationResult) {
        navigateToActivity()
    }

    private fun navigateToActivity() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}