package com.atlas.atlas.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.atlas.atlas.R
import com.atlas.atlas.auth.LoginActivity


class SplashActivity : Activity() {


    val defaultPassword = "dlkhokjsdjgvig0w875o344ugiasodpga"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val loginPref = getSharedPreferences("userLoginInfo", Context.MODE_PRIVATE)
        val user_id = loginPref.getInt("user_id", -1)
        val password = loginPref.getString("password", defaultPassword)
        if (user_id == -1 || password == defaultPassword) {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        } else {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        overridePendingTransition(0, 0)
        finish()
    }

    companion object {

        val class2: Class<*>
            get() = SplashActivity::class.java
    }
}
