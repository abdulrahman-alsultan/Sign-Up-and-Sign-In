package com.example.signupandsignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class SignIn : AppCompatActivity() {

    private val database by lazy { DBHelper(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val name = findViewById<EditText>(R.id.sign_in_et_name)
        val email = findViewById<EditText>(R.id.sign_in_et_email)
        val password = findViewById<EditText>(R.id.sign_in_et_password)
        val confirmPassword = findViewById<EditText>(R.id.sign_in_et_confirm_password)
        val sinIn = findViewById<Button>(R.id.sign_in_btn_sign_in)
        val logIn = findViewById<TextView>(R.id.sign_in_tv_log_in)

        val text = logIn.text.toString()
        val ss = SpannableString(text)
        val clickableSpan = object : ClickableSpan(){
            override fun onClick(p0: View) {
                startActivity(Intent(this@SignIn, MainActivity::class.java))
            }
        }
        ss.setSpan(clickableSpan, 17, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        logIn.text = ss
        logIn.movementMethod = LinkMovementMethod.getInstance()

        name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sinIn.isEnabled = t.toString().trim().isNotEmpty() && email.text.toString().trim().isNotEmpty() && password.text.toString().trim().isNotEmpty() && confirmPassword.text.toString().trim().isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sinIn.isEnabled = t.toString().trim().isNotEmpty() && name.text.toString().trim().isNotEmpty() && password.text.toString().trim().isNotEmpty() && confirmPassword.text.toString().trim().isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sinIn.isEnabled = t.toString().trim().isNotEmpty() && email.text.toString().trim().isNotEmpty() && name.text.toString().trim().isNotEmpty() && confirmPassword.text.toString().trim().isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(t: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sinIn.isEnabled = t.toString().trim().isNotEmpty() && email.text.toString().trim().isNotEmpty() && password.text.toString().trim().isNotEmpty() && name.text.toString().trim().isNotEmpty()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        sinIn.setOnClickListener {
            if (password.text.toString() == confirmPassword.text.toString()){
                val check = database.logIn(email.text.toString(), password.text.toString())
                if(check != "Account is not exist")
                    Toast.makeText(this, "Account is already exist.", Toast.LENGTH_LONG).show()
                else{
                    val res = database.signIN(name.text.toString(), email.text.toString(), password.text.toString())
                    if(res > 0){
                        Toast.makeText(this, "Account created successfully", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(this, "Password and Confirm Password must be match.", Toast.LENGTH_LONG).show()
            }
        }

    }
}