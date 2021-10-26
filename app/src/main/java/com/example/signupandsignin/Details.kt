package com.example.signupandsignin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val i = intent.getStringExtra("email")
        val sqLiteDatabase = DBHelper(applicationContext)
        val name = findViewById<TextView>(R.id.details_name)
        val email = findViewById<TextView>(R.id.details_email)
        val data = sqLiteDatabase.getDetails(i.toString())

        if(data.isNotEmpty()){
            name.text = "Name: ${data[0]}"
            email.text = "Email: ${data[1]}"
        }


    }
}