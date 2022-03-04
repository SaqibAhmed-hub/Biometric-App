package com.example.bioMetricApp.common

import androidx.biometric.BiometricPrompt

interface BiometricAuthListener {

    fun onBiometricAuthenticateError(error: Int,errMsg: String)
    fun onBiometericAuthenticateSuccess(result: BiometricPrompt.AuthenticationResult)

}