package com.atlas.atlas.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.atlas.atlas.R
import com.atlas.atlas.general.LinearProgressBar
import com.atlas.atlas.general.Utils
import com.atlas.atlas.main.MainActivity
import com.atlas.atlas.main.SplashActivity
import java.util.*

class LoginActivity : AppCompatActivity() {

    private var loginNameEditText: EditText? = null
    private var loginPassEditText: EditText? = null
    private var loginSubmit: Button? = null
    private var layout: ViewGroup? = null
    private var errorMessage: TextView? = null
    private var linearProgressBar: LinearProgressBar? = null
    private var loginLayout: ViewGroup? = null
    private var menuLayout: ViewGroup? = null
    private var inLogin = false
    private var pressed: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }


    fun registerButton(view: View) {

    }

    private fun init() {
        linearProgressBar = findViewById(R.id.linearProgressBar) as LinearProgressBar
        layout = findViewById(R.id.login_layout) as ViewGroup
        loginNameEditText = findViewById(R.id.login_username_input) as EditText
        loginPassEditText = findViewById(R.id.login_password_input) as EditText
        loginSubmit = findViewById(R.id.loginSubmitButton) as Button
        errorMessage = findViewById(R.id.loginErrorMessage) as TextView
        loginLayout = findViewById(R.id.loginLayout) as ViewGroup
        menuLayout = findViewById(R.id.menuLayout) as ViewGroup
    }

    fun goToLogin(view: View) {

        inLogin = true
        loginLayout?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.login_scale_in))
        loginLayout?.setVisibility(View.VISIBLE)
        menuLayout?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.login_scale_out))
        menuLayout?.setVisibility(View.GONE)
    }


    internal fun showError(message: Int) {
        errorMessage?.setText(message)
        if (linearProgressBar?.getVisibility() == View.VISIBLE || linearProgressBar?.getVisibility() == View.INVISIBLE) {
            linearProgressBar?.setVisibility(View.GONE)
        }
        if (errorMessage?.getVisibility() == View.GONE) {
            errorMessage?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
            errorMessage?.setVisibility(View.VISIBLE)
        }
        TransitionManager.beginDelayedTransition(layout)
    }


    fun submitLoginInfo(view: View) {
        if (!pressed) {
            val assumedPhoneNumber = loginNameEditText?.getText().toString().trim { it <= ' ' }
            val assumedPassword = loginPassEditText?.getText().toString().trim { it <= ' ' }
            if (!Utils.isNetworkAvailable(applicationContext)) {
                showError(R.string.noNetwork)

            } else if (assumedPhoneNumber != "" && assumedPassword != "") {
                if (errorMessage?.getVisibility() == View.VISIBLE) {
                    errorMessage?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                    errorMessage?.setVisibility(View.GONE)
                }
                if (linearProgressBar?.getVisibility() == View.GONE) {
                    linearProgressBar?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
                    linearProgressBar?.setVisibility(View.VISIBLE)
                }

                TransitionManager.beginDelayedTransition(layout)


                val stringRequest = object : StringRequest(Request.Method.POST, "http://social-cluster.com/login.php/", Response.Listener<String> { response ->
                    when (response) {
                        "empty" -> showError(R.string.loginEmpty)
                        "notExist" -> showError(R.string.loginNotExist)
                        "wrongPass" -> showError(R.string.loginWrongPass)
                        else -> {

                            pressed = true
                            val login = applicationContext.getSharedPreferences("userLoginInfo", Context.MODE_PRIVATE)
                            val loginEdit = login.edit()
                            val user_id = Integer.parseInt(response)
                            loginEdit.putInt("user_id", user_id)
                            loginEdit.putString("password", assumedPassword)
                            loginEdit.apply()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                            finish()
                        }
                    }
                }, Response.ErrorListener { }) {
                    @Throws(AuthFailureError::class)
                    override fun getParams(): Map<String, String> {
                        val values = HashMap<String, String>()
                        values.put("phone_number", assumedPhoneNumber)
                        values.put("password", assumedPassword)
                        return values
                    }
                }
                Volley.newRequestQueue(applicationContext).add(stringRequest)
            } else {
                //empty
                showError(R.string.loginEmpty)
            }
        }
    }

    override fun onBackPressed() {

        if (inLogin) {

            inLogin = false
            loginLayout?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.login_scale_out_back))
            loginLayout?.setVisibility(View.GONE)
            menuLayout?.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.login_scale_in_back))
            menuLayout?.setVisibility(View.VISIBLE)
        }
    }

}
