package com.example.signupandsignin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(ctx: Context): SQLiteOpenHelper(ctx, "users.db", null, 1) {

    private val sqLiteDatabase = writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS(NAME TEXT, EMAIL TEXT, PASSWORD TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}


    fun logIn(email: String, password: String): String{
        val emailExist = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE EMAIL = '$email'", null)
        if(emailExist.count > 0){
            val res = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE EMAIL = '$email' AND PASSWORD = '$password'", null)
            if(res.count > 0)
                return "true"
            return "Password not correct"
        }else{
            return "Account is not exist"
        }

    }

    fun signIN(name: String, email: String, password: String): Long{
        val cv = ContentValues()
        cv.put("NAME", name)
        cv.put("EMAIL", email)
        cv.put("PASSWORD", password)
        return sqLiteDatabase.insert("USERS", null, cv)
    }

    fun getDetails(e: String): List<String>{
        val c = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE EMAIL = '$e'", null)
        if(c.moveToFirst())
            return listOf(c.getString(0).toString(), c.getString(1).toString())
        return listOf(":(", ":(")
    }

}