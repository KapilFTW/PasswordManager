package com.codeshode.passwordmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codeshode.passwordmanager.util.BiometricHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val biometricHelper = BiometricHelper(this)
        if (biometricHelper.canAuthenticate()) {
            biometricHelper.showBiometricPrompt(onSuccess = {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, onFailure = {
                Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
            }, onError = {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            })
        }
        setContentView(R.layout.acivity_splash)
        val authButton = findViewById<Button>(R.id.auth_button)
        authButton.setOnClickListener {
            if (biometricHelper.canAuthenticate()) {
                biometricHelper.showBiometricPrompt(onSuccess = {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, onFailure = {
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }, onError = {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                })
            }
        }


    }
}