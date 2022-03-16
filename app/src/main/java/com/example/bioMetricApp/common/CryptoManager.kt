package com.example.bioMetricApp.common

import com.example.bioMetricApp.utils.EncryptedData
import javax.crypto.Cipher

interface CryptoManager {

    fun getInitilizedCipherForEncryption(key: String): Cipher
    fun getInitilizedCipherForDecryption(key: String, initilizedVector: ByteArray): Cipher
    fun encryptData(plainText: String, cipher: Cipher): EncryptedData
    fun decryptData(ciphertext: ByteArray, cipher: Cipher):String
}