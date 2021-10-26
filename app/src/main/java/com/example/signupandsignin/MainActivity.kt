package com.example.signupandsignin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val database by lazy { DBHelper(applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = findViewById<EditText>(R.id.et_login_email)
        val password = findViewById<EditText>(R.id.et_login_password)
        val login = findViewById<Button>(R.id.btn_login_login)
        val signIn = findViewById<TextView>(R.id.tv_login_sign_in)

        val text = signIn.text.toString()
        val ss = SpannableString(text)
        val clickableSpan1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(p0: View) {
                startActivity(Intent(this@MainActivity, SignIn::class.java))
            }
        }
        ss.setSpan(clickableSpan1, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        signIn.text = ss
        signIn.movementMethod = LinkMovementMethod.getInstance()

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                login.isEnabled =
                    t.toString().trim().isNotEmpty() && password.text.toString().trim().isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                login.isEnabled =
                    t.toString().trim().isNotEmpty() && email.text.toString().trim().isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        login.setOnClickListener {
            val e = email.text.toString()
            val p = password.text.toString()
            val res = database.logIn(e, p)

            if(res == "true"){
                val intent = Intent(this, Details::class.java)
                intent.putExtra("email", e)
                startActivity(intent)
            }
            else
                Toast.makeText(this, res, Toast.LENGTH_LONG).show()

        }

    }
}