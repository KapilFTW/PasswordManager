package com.codeshode.passwordmanager.ui.screens

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeshode.passwordmanager.data.model.Password
import com.codeshode.passwordmanager.repository.PasswordRepository
import com.codeshode.passwordmanager.util.EncryptionUtil
import com.codeshode.passwordmanager.util.KeyStoreHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val passwordRepository: PasswordRepository) :
    ViewModel() {
    companion object {
        const val TAG = "HOME_VM"
    }

    private val _passwords = MutableStateFlow(listOf<Password>())
    val passwords = _passwords.asStateFlow()
    private val _state = MutableStateFlow(HomeState.INITIAL_STATE)
    val state = _state.asStateFlow()

    fun changeState(state: HomeState) {
        _state.update { state }
    }

    fun addPassword(password: Password, context: Context) {
        viewModelScope.launch {
            var secretKey = KeyStoreHelper.getKey(context)
            if (secretKey== null){
                val generatedKey = EncryptionUtil.getBase64Key()
                KeyStoreHelper.storeKey(context, generatedKey)
                Log.i(TAG, "generated: $generatedKey  \nsecret: $secretKey \nsaved: ${KeyStoreHelper.getKey(context)}")
                secretKey = generatedKey
            }

            val encryptedPassword = EncryptionUtil.encrypt(secretKey, password.password)
            passwordRepository.upsertPassword(password.copy(password = encryptedPassword))
        }

    }

    fun getAllPasswords(context: Context) {
        viewModelScope.launch {
            val secretKey = KeyStoreHelper.getKey(context) ?: ""
            Log.i(TAG, "getAllPasswords- key: $secretKey")
            passwordRepository.getAllPasswords().collect { list ->
                Log.i(TAG, "getAllPasswords: $list")
                val decryptedList = mutableListOf<Password>()
                list.forEach {
                    decryptedList.add(it.copy(password = decryptPassword(it.password, secretKey)))
                }
                _passwords.update { decryptedList }
            }
        }
    }

    fun deletePassword(id: Int) {
        viewModelScope.launch {
            passwordRepository.deletePassword(id)
        }
    }

    private fun decryptPassword(encryptedPassword: String, secretKey: String): String {
        Log.i(TAG+" 2", "secret: $secretKey")
        return EncryptionUtil.decrypt(secretKey, encryptedPassword)
    }

    init {
    }
}

enum class HomeState {
    INITIAL_STATE,
    LOADING_STATE,
    ADD_STATE,
    DETAIL_STATE,
    EDIT_STATE
}