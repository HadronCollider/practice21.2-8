package com.example.recyclerview30

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val tv = findViewById<TextView>(R.id.textView5)
        tv.setText("adfafgasdfgafg")
    }
}