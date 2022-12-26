package com.example.webviewexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText: EditText = findViewById(R.id.enterEt)
        val btn: Button = findViewById(R.id.enterBtn)
        val intent = Intent(this, WebViewActivity::class.java)
        btn.setOnClickListener{
            intent.putExtra("url",editText.text.toString())
            startActivity(intent)
        }
    }
}