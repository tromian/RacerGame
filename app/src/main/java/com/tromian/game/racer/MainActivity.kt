package com.tromian.game.racer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnStart : Button = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {

            startActivity(Intent(this, GameActivity::class.java))

        }

    }


}