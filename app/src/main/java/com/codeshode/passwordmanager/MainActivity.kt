package com.codeshode.passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codeshode.passwordmanager.data.model.Password
import com.codeshode.passwordmanager.ui.components.PasswordCard
import com.codeshode.passwordmanager.ui.screens.HomeScreen
import com.codeshode.passwordmanager.ui.screens.HomeState
import com.codeshode.passwordmanager.ui.theme.PasswordManagerTheme
import com.codeshode.passwordmanager.util.EncryptionUtil
import com.codeshode.passwordmanager.util.KeyStoreHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (KeyStoreHelper.getKey(this)==null){
            val generatedKey = EncryptionUtil.getBase64Key()
            KeyStoreHelper.storeKey(this, generatedKey)
        }
        enableEdgeToEdge()
        setContent {
            PasswordManagerTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    PasswordCard(password = Password(accountType = "Google", username = "Kapil", password = "123456")) {
        
    }
}
