package com.codeshode.passwordmanager.util

import android.content.Context
import android.content.SharedPreferences

object KeyStoreHelper {

    private const val PREF_NAME = "EncryptionPrefs"
    private const val KEY_ALIAS = "encryptionKey"

    fun storeKey(context: Context, key: String) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_ALIAS, key).apply()
    }

    fun getKey(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_ALIAS, null)
    }
}