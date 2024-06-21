package com.codeshode.passwordmanager.util

import android.util.Base64
import android.util.Log
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtil {

    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val KEY_SIZE = 256
    private const val TAG_LENGTH = 128
    private const val IV_LENGTH = 12

    private fun generateKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM)
        keyGenerator.init(KEY_SIZE)
        return keyGenerator.generateKey()
    }

    private fun getSecretKeySpec(secret: String): SecretKeySpec {
        val decodedKey = Base64.decode(secret, Base64.DEFAULT)
        Log.d("ENCRYP_UTIL 2","secret: $secret \ndecoded: $decodedKey")
        return SecretKeySpec(decodedKey, 0, decodedKey.size, ALGORITHM)
    }

    fun encrypt(secret: String, data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKeySpec = getSecretKeySpec(secret)
        val iv = ByteArray(IV_LENGTH)
        SecureRandom().nextBytes(iv)
        val ivSpec = GCMParameterSpec(TAG_LENGTH, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec)
        val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        val combined = ByteArray(iv.size + encryptedData.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encryptedData, 0, combined, iv.size, encryptedData.size)
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    fun decrypt(secret: String, encryptedData: String): String {
        Log.d("ENCRYP_UTIL 2","secret: $secret \nencryptedString: $encryptedData")
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val secretKeySpec = getSecretKeySpec(secret)
        val combined = Base64.decode(encryptedData, Base64.DEFAULT)
        val iv = ByteArray(IV_LENGTH)
        System.arraycopy(combined, 0, iv, 0, iv.size)
        val ivSpec = GCMParameterSpec(TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec)
        val encryptedDataBytes = ByteArray(combined.size - iv.size)
        System.arraycopy(combined, iv.size, encryptedDataBytes, 0, encryptedDataBytes.size)
        val decryptedData = cipher.doFinal(encryptedDataBytes)
        return String(decryptedData, Charsets.UTF_8)
    }

    fun getBase64Key(): String {
        val key = generateKey()
        return Base64.encodeToString(key.encoded, Base64.DEFAULT)
    }
}