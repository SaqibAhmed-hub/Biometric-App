package com.example.bioMetricApp.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import com.example.bioMetricApp.R
import com.example.bioMetricApp.common.BiometricAuthListener
import com.example.bioMetricApp.utils.CryptoManagerImpl
import com.example.bioMetricApp.utils.BiometricUtils
import kotlinx.android.synthetic.main.activity_auth.*
import java.nio.charset.Charset

class AuthActivity : AppCompatActivity(), BiometricAuthListener {

    private var readyToEncrypt: Boolean = false
    private lateinit var cryptoManagerImpl: CryptoManagerImpl
    private lateinit var secretKeyName: String
    private lateinit var cipherText: ByteArray
    private lateinit var initilizedVector: ByteArray

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        cryptoManagerImpl = CryptoManagerImpl()
        secretKeyName = getString(R.string.secretKey)
        encrypt_button.setOnClickListener {
            authenticateToEncrypt()
        }
        decrypt_button.setOnClickListener {
            authenticateToDecrypt()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun authenticateToDecrypt() {
        readyToEncrypt = false
        if (output_view.text.isNullOrEmpty()){
            Toast.makeText(this, "Please Encrypt First to Decrypt", Toast.LENGTH_SHORT).show()
        }else{
            if (BiometricUtils.isBiometricReady(this)) {
                val cipher = cryptoManagerImpl.getInitilizedCipherForDecryption(
                    secretKeyName,
                    initilizedVector
                )
                BiometricUtils.showBiometricPrompt(
                    activity = this,
                    listener = this,
                    cryptoObject = BiometricPrompt.CryptoObject(cipher)
                )

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun authenticateToEncrypt() {
        readyToEncrypt = true
        if (input_view.text.isNullOrEmpty()) {
            Toast.makeText(this, "Please Enter the Text to Encrypt", Toast.LENGTH_SHORT).show()
        } else {
            if (BiometricUtils.isBiometricReady(this)) {
                val cipher = cryptoManagerImpl.getInitilizedCipherForEncryption(secretKeyName)
                BiometricUtils.showBiometricPrompt(
                    activity = this,
                    listener = this,
                    cryptoObject = BiometricPrompt.CryptoObject(cipher)
                )
            }
        }
    }

    override fun onBiometricAuthenticateError(error: Int, errMsg: String) {
        Log.e("AuthActivity", "onBiometricAuthenticateError: $errMsg")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBiometricAuthenticateSuccess(result: BiometricPrompt.AuthenticationResult) {
        processData(result.cryptoObject)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun processData(cryptoObject: BiometricPrompt.CryptoObject?) {
        val data = if (readyToEncrypt) {
            val text = input_view.text.toString()
            val encryptedData = cryptoManagerImpl.encryptData(text, cryptoObject?.cipher!!)
            cipherText = encryptedData.ciphertext
            initilizedVector = encryptedData.initializationVector
            String(cipherText, Charset.forName("UTF-8"))
        } else {
            cryptoManagerImpl.decryptData(cipherText, cryptoObject?.cipher!!)
        }
        output_view.text = data
    }
}