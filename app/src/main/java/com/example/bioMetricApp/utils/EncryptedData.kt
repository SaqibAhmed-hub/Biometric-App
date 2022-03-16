package com.example.bioMetricApp.utils

data class EncryptedData(
    val ciphertext: ByteArray,
    val initializationVector: ByteArray
)
